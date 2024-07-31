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


}