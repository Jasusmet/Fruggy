package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.servicios.SrvcValSupermercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ValSupermercadoCtrl {

    @Autowired
    private SrvcValSupermercado valsupermercadosSrvc;

    @GetMapping("/valsupermercados")
    public String listarValSupermercados(Model model) {
        List<ValSupermercado> listaValSupermercados = valsupermercadosSrvc.buscarEntidades();
        model.addAttribute("valsupermercados", listaValSupermercados);
        return "valsupermercados";
    }

    @GetMapping("/valsupermercados/{id}")
    public String mostrarValsupermercado(@PathVariable int id, Model model) {
        Optional<ValSupermercado> valsupermercado = valsupermercadosSrvc.encuentraPorId(id);
        model.addAttribute("valsupermercado", valsupermercado);
        return "redirect:/valsupermercados";
    }

    @PostMapping("/valsupermercados")
    public String crearValsupermercado(@RequestBody ValSupermercado valsupermercado, Model model) {
        try {
            valsupermercadosSrvc.guardar(valsupermercado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/valsupermercados";
    }

    @DeleteMapping("/valsupermercados/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarValsupermercado(@PathVariable int id, Model model) {
        valsupermercadosSrvc.eliminarPorId(id);
        return "redirect:/valsupermercados";
    }
}
