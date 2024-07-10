package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.servicios.SrvcCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriaCtrl {

    @Autowired
    private SrvcCategoria categoriasSrvc;

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categoria> listaCategorias = categoriasSrvc.listaCategorias();
        model.addAttribute("categorias", listaCategorias);
        return "categorias";
    }

    @PostMapping("/categorias")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearCategoria(@RequestBody Categoria categoria, Model model) {
        categoriasSrvc.guardarCategoria(categoria);
        return "redirect:/categorias";
    }

    @DeleteMapping("/categorias/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarCategoria(@PathVariable int id, Model model) {
        categoriasSrvc.eliminarCategoria(id);
        return "redirect:/categorias";
    }

}
