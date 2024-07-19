package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.servicios.SrvcDescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DescuentoCtrl {

    @Autowired
    private SrvcDescuento descuentosSrvc;

    @GetMapping("/descuentos")
    public String listarDescuentos(Model model) {
        List<Descuento> listaDescuentos = descuentosSrvc.buscarEntidades();
        model.addAttribute("descuentos", listaDescuentos);
        return "descuentos";
    }

    @GetMapping("/descuentos/{id}")
    public String mostrarDescuento(@PathVariable int id, Model model) {
        Optional<Descuento> descuento = descuentosSrvc.encuentraPorId(id);
        model.addAttribute("descuento", descuento);
        return "redirect:/descuentos";
    }

    @PostMapping("/descuentos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearDescuento(@RequestBody Descuento descuento, Model model) {
        try {
            descuentosSrvc.guardar(descuento);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/descuentos";
    }

    @DeleteMapping("/descuentos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarDescuento(@PathVariable int id, Model model) {
        descuentosSrvc.eliminarPorId(id);
        return "redirect:/descuentos";
    }
}
