package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PrecioCtrl {

    @Autowired
    private SrvcPrecio preciosSrvc;

    @GetMapping("/precios")
    public String listarPrecios(Model model) {
        List<Precio> listaPrecios = preciosSrvc.buscarEntidades();
        model.addAttribute("precios", listaPrecios);
        return "precios";
    }

    @GetMapping("/precios/{id}")
    public String mostrarPrecio(@PathVariable Integer id, Model model) {
        Optional<Precio> precio = preciosSrvc.encuentraPorId(Long.valueOf(id));
        model.addAttribute("precio", precio);
        return "redirect:/precios";
    }

    @PostMapping("/precios")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearPrecio(@RequestBody Precio precio, Model model) {
        try {
            preciosSrvc.guardar(precio);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/precios";
    }

    @DeleteMapping("/precios/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarPrecio(@PathVariable Integer id, Model model) {
        preciosSrvc.eliminarPorId(Long.valueOf(id));
        return "redirect:/precios";
    }
}
