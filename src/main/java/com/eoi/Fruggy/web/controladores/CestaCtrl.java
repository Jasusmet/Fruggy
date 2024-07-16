package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.servicios.SrvcCesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CestaCtrl {

    @Autowired
    private SrvcCesta cestasSrvc;

    @GetMapping("/cesta")
    public String listarCestas(Model model) {
        List<Cesta> listaCestas = cestasSrvc.buscarEntidades();
        model.addAttribute("cestas", listaCestas);
        return "cesta";
    }

    @GetMapping("/cesta/{id}")
    public String mostrarCesta(@PathVariable int id, Model model) {
        Optional<Cesta> cesta = cestasSrvc.encuentraPorId(id);
        model.addAttribute("cesta", cesta);
        return "redirect:/cesta";
    }

    @PostMapping("/cesta")
    public String crearCesta(@RequestBody Cesta cesta, Model model) {
        try {
            cestasSrvc.guardar(cesta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/cesta";
    }

    @DeleteMapping("/cesta/{id}")
    public String eliminarCesta(@PathVariable int id, Model model) {
        cestasSrvc.eliminarPorId(id);
        return "redirect:/cesta";
    }

    @GetMapping("/cesta/detalles")
    public String CestaDetallesCtrl() {
        return "cesta-detalles";
    }
}
