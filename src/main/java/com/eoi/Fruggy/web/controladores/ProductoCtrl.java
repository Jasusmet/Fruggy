package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/productos")
public class ProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcValProducto valProductosSrvc;
    private final SrvcCesta cestaSrvc;
    private final SrvcCategoria categoriaSrvc;
    private final SrvcUsuario usuarioSrvc;
    private final SrvcPrecio precioSrvc;

    public ProductoCtrl(SrvcProducto productosSrvc, SrvcValProducto valProductosSrvc, SrvcCesta cestaSrvc, SrvcCategoria categoriaSrvc, SrvcUsuario usuarioSrvc, SrvcPrecio precioSrvc) {
        this.productosSrvc = productosSrvc;
        this.valProductosSrvc = valProductosSrvc;
        this.cestaSrvc = cestaSrvc;
        this.categoriaSrvc = categoriaSrvc;
        this.usuarioSrvc = usuarioSrvc;
        this.precioSrvc = precioSrvc;
    }

    @GetMapping
    public String mostrarCatalogo(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "default") String sort,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) Long categoriaId,
                                  Model model, @AuthenticationPrincipal Usuario usuario) {

        Page<Producto> paginaProductos;

        // Si hay una búsqueda por nombre, utilizar el método de búsqueda
        if (search != null && !search.isEmpty()) {
            paginaProductos = productosSrvc.buscarProductosPorNombre(search, page, size);
        } else if (categoriaId != null) {
            // Si hay un filtro por categoría
            paginaProductos = productosSrvc.buscarProductosPorCategoria(categoriaId, page, size);
        } else {
            // Filtros de ordenación
            switch (sort) {
                case "precioAsc":
                    paginaProductos = productosSrvc.obtenerProductosPorPrecioAscendente(page, size);
                    break;
                case "precioDesc":
                    paginaProductos = productosSrvc.obtenerProductosPorPrecioDescendente(page, size);
                    break;
                case "novedades":
                    paginaProductos = productosSrvc.obtenerProductosPorNovedades(page, size);
                    break;
                case "mejorPuntuacion":
                    paginaProductos = productosSrvc.obtenerProductosPorMejorPuntuacion(page, size);
                    break;
                default:
                    paginaProductos = productosSrvc.obtenerProductosPaginados(page, size);
            }
        }
        // Calcular la nota media para cada producto
        if (!paginaProductos.isEmpty()) {
            paginaProductos.forEach(producto -> {
                Double notaMedia = valProductosSrvc.calcularNotaMedia(producto.getId());
                producto.setNotaMedia(notaMedia); // Asegúrate de que hay un setter para notaMedia
            });
        }

        if (paginaProductos.isEmpty()) {
            model.addAttribute("mensaje", "No existe este producto.");
        } else {
            model.addAttribute("mensaje", null);
        }

        List<Categoria> categorias = categoriaSrvc.buscarEntidades();
        List<Cesta> cestas = cestaSrvc.buscarEntidades();

        model.addAttribute("pagina", paginaProductos);
        model.addAttribute("cestas", cestas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaProductos.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("categoriaId", categoriaId);

        return "/productos/productos";
    }

    @GetMapping("/categoria/{id}")
    public String mostrarProductosPorCategoria(@PathVariable Long id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               Model model) {
        Page<Producto> productosPorCategoria = productosSrvc.buscarProductosPorCategoria(id, page, size);
        Categoria categoriaActual = categoriaSrvc.encuentraPorId(id).orElse(null);

        List<Cesta> cestas = cestaSrvc.buscarEntidades();
        if (cestas == null) {
            cestas = new ArrayList<>();
        }

        model.addAttribute("pagina", productosPorCategoria);
        model.addAttribute("categoriaActual", categoriaActual);
        model.addAttribute("categorias", categoriaSrvc.buscarEntidades());
        model.addAttribute("cestas", cestas);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productosPorCategoria.getTotalPages());

        return "/productos/productos";
    }

    @GetMapping("/detalles/{id}")
    public String verDetallesProducto(@PathVariable("id") Long productoId, Model model) throws Throwable {
        Producto producto = productosSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + productoId));
        List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);

        // Calcular la nota media
        Double notaMedia = valProductosSrvc.calcularNotaMedia(productoId);
        producto.setNotaMedia(notaMedia); // Asignar notaMedia al producto

        // Obtener productos similares por subcategoría
        List<Producto> productosSimilares = productosSrvc.buscarProductosSimilares(producto.getSubcategoria().getId(), productoId);

        model.addAttribute("producto", producto);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("notaMedia", notaMedia);
        model.addAttribute("valoracion", new ValoracionProducto());
        model.addAttribute("productosSimilares", productosSimilares);

        return "productos/detalles-producto";
    }

    @PostMapping("/valoraciones/{productoId}/guardar")
    public String guardarValoracion(@PathVariable Long productoId,
                                    @Valid @ModelAttribute("valoracion") ValoracionProducto valoracion,
                                    BindingResult result,
                                    @AuthenticationPrincipal Usuario usuario,
                                    Model model) {
        if (result.hasErrors()) {
            Producto producto = productosSrvc.encuentraPorId(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + productoId));
            List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("producto", producto);
            return "productos/detalles-producto";
        }

        // Obtén el usuario autenticado utilizando Optional
        Optional<Usuario> usuarioOptional = Optional.ofNullable(usuario);
        if (usuarioOptional.isEmpty()) {
            model.addAttribute("error", "Usuario no autenticado.");
            Producto producto = productosSrvc.encuentraPorId(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + productoId));
            List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("producto", producto);
            return "productos/detalles-producto";
        }

        valoracion.setUsuario(usuarioOptional.get());

        Producto producto = productosSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto inválido: " + productoId));
        valoracion.setProducto(producto);

        try {
            valProductosSrvc.guardar(valoracion);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("producto", producto);
            return "productos/detalles-producto";
        }

        return "redirect:/productos/detalles/" + productoId;
    }
}
