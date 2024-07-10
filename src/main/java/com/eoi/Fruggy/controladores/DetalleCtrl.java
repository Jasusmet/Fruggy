package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DetalleCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;

    @GetMapping("/detalles")
    public String listarDetalles(Model model) {
        List<Detalle> listaDetalles = detallesSrvc.listaDetalles();
        model.addAttribute("detalles", listaDetalles);
        return "detalles";
    }

    @GetMapping("/detalles/{id}")
    public String mostrarDetalle(@PathVariable int id, Model model) {
        Optional<Detalle> detalle = detallesSrvc.porIdDetalle(id);
        model.addAttribute("detalle", detalle);
        return "redirect:/detalles";
    }

    @PostMapping("/detalles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearDetalle(@RequestBody Detalle detalle, Model model) {
        detallesSrvc.guardarDetalle(detalle);
        return "redirect:/detalles";
    }

    @DeleteMapping("/detalles/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarDetalle(@PathVariable int id, Model model) {
        detallesSrvc.eliminarDetalle(id);
        return "redirect:/detalles";
    }
}
