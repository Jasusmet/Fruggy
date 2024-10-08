package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Favorito;
import com.eoi.Fruggy.entidades.Favorito;
import com.eoi.Fruggy.servicios.SrvcFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FavoritoCtrl {

    @Autowired
    private SrvcFavorito favoritosSrvc;

    @GetMapping("/favoritos")
    public String listarFavoritos(Model model) {
        List<Favorito> listaFavoritos = favoritosSrvc.buscarEntidades();
        model.addAttribute("favoritos", listaFavoritos);
        return "favorito";
    }

    @GetMapping("/favoritos/{id}")
    public String mostrarFavorito(@PathVariable int id, Model model) {
        Optional<Favorito> favorito = favoritosSrvc.encuentraPorId(id);
        model.addAttribute("favorito", favorito);
        return "redirect:/favoritos";
    }

    @PostMapping("/favoritos")
    public String crearFavorito(@RequestBody Favorito favorito, Model model) {
        try {
            favoritosSrvc.guardar(favorito);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/favoritos";
    }

    @DeleteMapping("/favoritos/{id}")
    public String eliminarFavorito(@PathVariable int id, Model model) {
        favoritosSrvc.eliminarPorId(id);
        return "redirect:/favoritos";
    }
}
