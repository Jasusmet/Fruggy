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

    public ProductoCtrl(SrvcProducto productosSrvc, SrvcPrecio preciosSrvc, SrvcImagen imagenSrvc, SrvcCategoria categoriasSrvc, SrvcSubcategoria subcategoriasSrvc, SrvcValProducto valProductosSrvc) {
        this.productosSrvc = productosSrvc;
        this.preciosSrvc = preciosSrvc;
        this.imagenSrvc = imagenSrvc;
        this.categoriasSrvc = categoriasSrvc;
        this.subcategoriasSrvc = subcategoriasSrvc;
        this.valProductosSrvc = valProductosSrvc;
    }

    @RequestMapping
    public String producto(Model model) {
        List<Producto> listaProductos = productosSrvc.getRepo().findAll();
        for (Producto producto : listaProductos) {
            Descuento descuento = producto.getDescuento();  // Asumiendo que `Producto` tiene un método `getDescuento()`
            if (descuento != null) {
                Double precioOriginal = producto.getProductoPrecios().getValor();
                Double porcentajeDescuento = descuento.getDescuentoTipoDescuento().getPorcentaje();
                Double precioConDescuento = precioOriginal - (precioOriginal * (porcentajeDescuento / 100.0));

                // Añadir al producto la información del descuento
                producto.setPrecioConDescuento(precioConDescuento);
                producto.setPorcentajeDescuento(porcentajeDescuento);
            } else {
                // Si no hay descuento, asigna los valores originales
                producto.setPrecioConDescuento(producto.getProductoPrecios().getValor());
                producto.setPorcentajeDescuento(0.0);
            }
        }
        model.addAttribute("listaProducto", listaProductos);
        return "";
    }

}
