package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.servicios.SrvcProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductoCtrl {

    @Autowired
    private SrvcProducto srvcProducto;

    @GetMapping("/productos")
    public String producto(Model model) {
        List<Producto> listaProductos = srvcProducto.listaProductos();
        model.addAttribute("listaProducto", listaProductos);
        return "productos";
    }

    @GetMapping("/productos/{id}")
    public String mostrarProducto(@PathVariable int id, Model model) {
        if (srvcProducto.porIdProductos(id).isPresent()) {
            model.addAttribute("producto", srvcProducto.porIdProductos(id).get());
        } else {
            model.addAttribute("producto", "No tenemos datos de este producto");
        }
        return "redirect:/productos";
    }

    @PostMapping("/productos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearProducto(@RequestBody Producto producto, Model model) {
        srvcProducto.guardarProductos(producto);
        return "redirect:/productos";
    }

    @DeleteMapping("/productos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarProducto(@PathVariable int id, Model model) {
        srvcProducto.eliminarProductos(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/actualizar-producto")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String ActualizarProductoCtrl() {
        return "actualizar-producto";
    }

}
