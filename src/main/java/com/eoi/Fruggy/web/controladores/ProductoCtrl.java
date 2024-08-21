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
import java.time.LocalDateTime;
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

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String mostrarCatalogo(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model, @AuthenticationPrincipal Usuario usuario) {
        Page<Producto> paginaProductos = productosSrvc.obtenerProductosPaginados(page, size);
        List<Categoria> categorias = categoriaSrvc.buscarEntidades();
        List<Cesta> cestas = cestaSrvc.buscarEntidades();

        // Calcular la nota media para cada producto
        for (Producto producto : paginaProductos) {
            Double notaMedia = valProductosSrvc.calcularNotaMedia(producto.getId());
            producto.setNotaMedia(notaMedia);
        }

        model.addAttribute("pagina", paginaProductos);
        model.addAttribute("cestas", cestas);
        model.addAttribute("categorias", categorias);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaProductos.getTotalPages());
        return "/productos/productos";
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detalles/{id}")
    public String verDetallesProducto(@PathVariable("id") Long productoId, Model model) throws Throwable {
        Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + productoId));
        List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);

        // Calcular la nota media
        Double notaMedia = valProductosSrvc.calcularNotaMedia(productoId);

        model.addAttribute("producto", producto);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("notaMedia", notaMedia);
        model.addAttribute("valoracion", new ValoracionProducto());
        return "productos/detalles-producto";
    }

    // hay que añadir el usuario loggeado para que un usuario solo pueda hacer una reseña y que nos muestre quien ha dejado el comentario.
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/valoraciones/{productoId}/guardar")
    public String guardarValoracion(@PathVariable Long productoId,
                                    @Valid @ModelAttribute("valoracion") ValoracionProducto valoracion,
                                    BindingResult result,
                                    Principal principal,
                                    Model model) throws Throwable {
        if (result.hasErrors()) {
            Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + productoId));
            List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("producto", producto);
            return "redirect:/productos/detalles";
        }

        String username = principal.getName();
        Usuario usuario = usuarioSrvc.getRepo().findByEmail(username);
        valoracion.setUsuario(usuario);

        Producto producto = (Producto) productosSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + productoId));
        valoracion.setProducto(producto);

        valProductosSrvc.guardar(valoracion);
        return "redirect:/productos/detalles/" + productoId;
    }
}