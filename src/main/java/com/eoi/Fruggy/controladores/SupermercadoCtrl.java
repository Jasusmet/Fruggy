package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SupermercadoCtrl {

    @Autowired
    private SrvcSupermercado supermercadosSrvc;

    @GetMapping("/supermercados")
    public String listarSupermercados(Model model) {
        List<Supermercado> listaSupermercados = supermercadosSrvc.buscarEntidades();
        model.addAttribute("supermercados", listaSupermercados);
        return "supermercados";
    }

    @GetMapping("/supermercados/{id}")
    public String mostrarSupermercado(@PathVariable int id, Model model) {
        Optional<Supermercado> supermercado = supermercadosSrvc.encuentraPorId(id);
        model.addAttribute("supermercado", supermercado);
        return "redirect:/supermercados";
    }

    @PostMapping("/supermercados")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearSupermercado(@RequestBody Supermercado supermercado, Model model) {
        try {
            supermercadosSrvc.guardar(supermercado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/supermercados";
    }

    @DeleteMapping("/supermercados/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarSupermercado(@PathVariable int id, Model model) {
        supermercadosSrvc.eliminarPorId(id);
        return "redirect:/supermercados";
    }

}

