package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.DTO.SubcategoriaDTO;
import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import com.eoi.Fruggy.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/productos")
public class ADMINProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcPrecio preciosSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcSubcategoria subcategoriasSrvc;
    private final SrvcCategoria categoriasSrvc;
    private final RepoPrecio precioSrvc;
    private final SrvcSupermercado supermercadoSrvc;


    public ADMINProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio preciosSrvc, SrvcImagen imagenSrvc, SrvcSubcategoria subcategoriasSrvc,
                             RepoPrecio repoPrecio, SrvcCategoria categoriasSrvc, RepoPrecio precioSrvc, SrvcSupermercado supermercadoSrvc) {
        this.productosSrvc = productosSrvc;
        this.preciosSrvc = preciosSrvc;
        this.imagenSrvc = imagenSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.precioSrvc = precioSrvc;
        this.supermercadoSrvc = supermercadoSrvc;
    }

    // Mostrar lista de productos
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productosSrvc.buscarEntidades();
        model.addAttribute("productos", productos);
        return "/admin/CRUD-Productos";
    }


    @GetMapping("/agregar")
    public String mostrarFormularioCreacion(Model model) {
        Producto producto = new Producto();
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        model.addAttribute("producto", producto);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("categorias", categorias);
        model.addAttribute("supermercados", supermercados);
        return "/admin/crear-producto";
    }

    //  formulario de creación de producto
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute("producto") Producto producto,
                                  BindingResult result,
                                  @RequestParam("precio") String precioValor,
                                  @RequestParam("subcategoria.id") Long subcategoriaId,
                                  @RequestParam("supermercado.id") Long supermercadoId) throws Exception {
        if (result.hasErrors()) {
            return "/admin/crear-producto";
        }

        // Asegúrate de que el campo activo tenga un valor
        if (producto.getActivo() == null) {
            producto.setActivo(true); // o false, dependiendo de tu lógica de negocio
        }
        Optional<Subcategoria> subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId);
        if (subcategoria.isPresent()) {
            producto.setSubcategoria(subcategoria.get());
        }
        // Guardar el producto primero para que tenga un ID
        Producto productoGuardado = productosSrvc.guardar(producto);

        Precio precio = new Precio();
        precio.setProducto(productoGuardado);
        precio.setValor(Double.parseDouble(precioValor.replace(",", ".")));
        precio.setSupermercado((Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId).orElse(null));
        precio.setActivo(true);

        preciosSrvc.guardar(precio); // Guardar el precio
        return "redirect:/admin/productos";
    }

    // Mostrar formulario para editar un producto
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        List<Subcategoria> subcategorias = subcategoriasSrvc.buscarEntidades();
        List<Categoria> categorias = categoriasSrvc.buscarEntidades();
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
        }
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("categorias", categorias);
        model.addAttribute("supermercados", supermercados);
        return "/admin/crear-producto";
    }

    // post de edición de producto
    @PostMapping("/editar/{id}")
    public String guardarEdicionProducto(@PathVariable Long id,
                                         @Valid @ModelAttribute("producto") Producto producto,
                                         BindingResult result,
                                         @RequestParam("precio") String precioValor,
                                         @RequestParam("subcategoria.id") Long subcategoriaId,
                                         @RequestParam("supermercado.id") Long supermercadoId) throws Exception {
        if (result.hasErrors()) {
            return "/admin/crear-producto";
        }
        // Asegúrate de que el campo activo tenga un valor
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        Optional<Subcategoria> subcategoria = subcategoriasSrvc.encuentraPorId(subcategoriaId);
        if (subcategoria.isPresent()) {
            producto.setSubcategoria(subcategoria.get());
        }

        // Guardar el producto primero para que tenga un ID
        Producto productoGuardado = productosSrvc.guardar(producto);

        Precio precio = new Precio();
        precio.setProducto(productoGuardado);
        precio.setValor(Double.parseDouble(precioValor.replace(",", ".")));
        precio.setSupermercado((Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId).orElse(null));
        precio.setActivo(true);

        preciosSrvc.guardar(precio); // Guardar el precio

        return "redirect:/admin/productos";
    }

    // Eliminar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productosSrvc.eliminarPorId(id);
        return "redirect: /admin/productos"; // Redirige a la lista de productos
    }
}
