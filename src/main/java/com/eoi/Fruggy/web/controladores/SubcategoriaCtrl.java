package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.servicios.SrvcSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
// es mejor ponerle un request mapping?
public class SubcategoriaCtrl {

    @Autowired
    private SrvcSubcategoria subcategoriasSrvc;

    @GetMapping
    public String listarSubcategorias(Model model) {
        List<Subcategoria> listaSubcategorias = subcategoriasSrvc.getRepo().findAll();
        model.addAttribute("listaSubcategorias", listaSubcategorias);
        return "subcategorias/lista";
    }

    @GetMapping("/agregar-subcategoria")
    public String agregarSubcategoria(Model model) {
        model.addAttribute("subcategoria", new Subcategoria());
        return "subcategorias/form";//Crearlo
    }

    @PostMapping("/guardar-subcategoria")
    public String guardarSubcategoria(@ModelAttribute Subcategoria subcategoria) throws Exception {
        subcategoriasSrvc.guardar(subcategoria);
        return "redirect:/subcategorias";
    }
    @GetMapping("/editar-subcategoria/{id}")
    public String editarSubcategoria(@PathVariable("id") long id, Model model) {
        Optional <Subcategoria> subcategoria = subcategoriasSrvc.getRepo().findById(id);
        model.addAttribute("subcategoria", subcategoria);
        return "subcategorias/form"; // hay que crear
    }
    @PostMapping("/eliminar-subcategoria/{id}")
    public String eliminarSubcategoria(@PathVariable("id") long id) {
        subcategoriasSrvc.getRepo().deleteById(id);
        return "redirect:/subcategorias";
    }

}
