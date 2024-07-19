package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.servicios.SrvcLista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ListaCtrl {

    @Autowired
    private SrvcLista listasSrvc;

    @GetMapping("/listas")
    public String listarListas(Model model) {
        List<Lista> listaListas = listasSrvc.buscarEntidades();
        model.addAttribute("listas", listaListas);
        return "listas";
    }

    @GetMapping("/listas/{id}")
    public String mostrarLista(@PathVariable int id, Model model) {
        Optional<Lista> lista = listasSrvc.encuentraPorId(id);
        model.addAttribute("lista", lista);
        return "redirect:/listas";
    }

    @PostMapping("/listas")
    public String crearLista(@RequestBody Lista lista, Model model) {
        try {
            listasSrvc.guardar(lista);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/listas";
    }

    @DeleteMapping("/listas/{id}")
    public String eliminarLista(@PathVariable int id, Model model) {
        listasSrvc.eliminarPorId(id);
        return "redirect:/listas";
    }
}
