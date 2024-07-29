package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.DTO.SubcategoriaDTO;
import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Producto> productosConDescuento = productosSrvc.getRepo().findByDescuentoActivoTrue();

        model.addAttribute("listaProducto", listaProductos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("subcategorias", subcategorias);
        model.addAttribute("supermercados", supermercados);
        model.addAttribute("productosConDescuento", productosConDescuento);

        // Verificar que modelo no esté vacío
        System.out.println("Productos: " + listaProductos.size());
        System.out.println("Categorías: " + categorias.size());
        System.out.println("Subcategorías: " + subcategorias.size());
        System.out.println("Supermercados: " + supermercados.size());
        System.out.println("Productos con descuento: " + productosConDescuento.size());

        return "catalogoProductos"; //
    }
}
