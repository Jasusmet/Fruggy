package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.servicios.SrvcSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubcategoriaCtrl {

    @Autowired
    private SrvcSubcategoria subcategoriasSrvc;

    @GetMapping("/subcategorias")
    public String listarSubcategorias(Model model) {
        List<Subcategoria> listaSubcategorias = subcategoriasSrvc.buscarEntidades();
        model.addAttribute("subcategorias", listaSubcategorias);
        return "subcategorias";
    }
}
