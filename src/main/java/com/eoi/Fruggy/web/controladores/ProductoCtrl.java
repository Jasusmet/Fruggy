package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/productos")
public class ProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcPrecio preciosSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcCategoria categoriasSrvc;
    private final SrvcSubcategoria subcategoriasSrvc;
    private final SrvcValProducto valProductosSrvc;
    private final SrvcSupermercado supermercadosSrvc;

    public ProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio preciosSrvc, SrvcImagen imagenSrvc, SrvcCategoria categoriasSrvc, SrvcSubcategoria subcategoriasSrvc, SrvcValProducto valProductosSrvc, SrvcSupermercado supermercadosSrvc) {
        this.productosSrvc = productosSrvc;
        this.preciosSrvc = preciosSrvc;
        this.imagenSrvc = imagenSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
        this.valProductosSrvc = valProductosSrvc;
        this.supermercadosSrvc = supermercadosSrvc;
    }

    @GetMapping
    public String producto(Model model) {
        List<Producto> listaProductos = productosSrvc.getRepo().findAll();
        List<Categoria> categorias = categoriasSrvc.getRepo().findAll();
        List<Subcategoria> subcategorias = subcategoriasSrvc.getRepo().findAll();
        List<Supermercado> supermercados = supermercadosSrvc.getRepo().findAll();
        //List<Producto> productosConDescuento = productosSrvc.getRepo().findByDescuentoActivoTrue();

        model.addAttribute("listaProducto", listaProductos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("supermercados", supermercados);
        //model.addAttribute("productosConDescuento", productosConDescuento);

        // Verificar que modelo no esté vacío
        System.out.println("Productos: " + listaProductos.size());
        System.out.println("Categorías: " + categorias.size());
        System.out.println("Subcategorías: " + subcategorias.size());
        System.out.println("Supermercados: " + supermercados.size());
      //  System.out.println("Productos con descuento: " + productosConDescuento.size());

        return "/productos/catalogoProductos"; //
    }
    @GetMapping("/{id}")
    public String detallesProducto(@PathVariable("id") Long id, Model model) {
        Optional<Producto> productoOptional = productosSrvc.encuentraPorId(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            Optional<ValProducto> valoraciones = valProductosSrvc.encuentraPorId(id);
            double notaMedia = valoraciones.stream().mapToDouble(ValProducto::getNota).average().orElse(0.0);
            model.addAttribute("producto", producto);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("notaMedia", notaMedia);
            model.addAttribute("nuevaValoracion", new ValProducto());
            return "/productos/detalles-producto";
        } else {
            return "redirect:/productos";
        }
    }
    @PostMapping("/valorar/{id}")
    public String valorarProducto(@PathVariable("id") Long id, @Valid @ModelAttribute("nuevaValoracion") ValProducto nuevaValoracion, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/productos/" + id;
        }
        Optional<Producto> productoOptional = productosSrvc.encuentraPorId(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            nuevaValoracion.setProducto(producto);
            valProductosSrvc.guardar(nuevaValoracion);
        }
        return "redirect:/productos/" + id;
    }
}