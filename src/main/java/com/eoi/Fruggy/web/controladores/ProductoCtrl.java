package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.servicios.SrvcProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoCtrl {

    @Autowired
    private SrvcProducto productosSrvc;

    @GetMapping("/productos")
    public String producto(Model model) {
        List<Producto> listaProductos = productosSrvc.buscarEntidades();
        model.addAttribute("listaProducto", listaProductos);
        return "productos";
    }

    @GetMapping("/productos/{id}")
    public String mostrarProducto(@PathVariable Integer id, Model model) {
        Optional<Producto> producto = productosSrvc.encuentraPorId(id);
        model.addAttribute("producto", producto);
        return "redirect:/productos";
    }

    @PostMapping("/productos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearProducto(@RequestBody Producto producto, Model model) {
        try {
            productosSrvc.guardar(producto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/productos";
    }

    @DeleteMapping("/productos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarProducto(@PathVariable int id, Model model) {
        productosSrvc.eliminarPorId(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/actualizar-producto")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String ActualizarProductoCtrl() {
        return "actualizar-producto";
    }

}
