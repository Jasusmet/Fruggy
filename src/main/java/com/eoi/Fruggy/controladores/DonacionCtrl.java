package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.servicios.SrvcDonacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DonacionCtrl {

    @Autowired
    private SrvcDonacion donacionesSrvc;

    @GetMapping("/donaciones")
    public String listarDonaciones(Model model) {
        List<Donacion> listaDonaciones = donacionesSrvc.buscarEntidades();
        model.addAttribute("donaciones", listaDonaciones);
        return "donaciones";
    }

    @GetMapping("/donaciones/{id}")
    public String mostrarDonacion(@PathVariable int id, Model model) {
        Optional<Donacion> donacion = donacionesSrvc.encuentraPorId(id);
        model.addAttribute("donacion", donacion);
        return "redirect:/donaciones";
    }

    @PostMapping("/donaciones")
    public String crearDonacion(@RequestBody Donacion donacion, Model model) {
        try {
            donacionesSrvc.guardar(donacion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/donacions";
    }

    @DeleteMapping("/donaciones/{id}")
    public String eliminarDonacion(@PathVariable int id, Model model) {
        donacionesSrvc.eliminarPorId(id);
        return "redirect:/donaciones";
    }
}
