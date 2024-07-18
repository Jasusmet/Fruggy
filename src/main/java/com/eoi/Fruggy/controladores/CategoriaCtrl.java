package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.servicios.SrvcCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaCtrl {

    @Autowired
    private SrvcCategoria categoriasSrvc;

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categoria> listaCategorias = categoriasSrvc.buscarEntidades();
        model.addAttribute("categorias", listaCategorias);
        return "categorias";
    }

    @PostMapping("/categorias")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearCategoria(@RequestBody Categoria categoria, Model model) {
        try {
            categoriasSrvc.guardar(categoria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/categorias";
    }

    @DeleteMapping("/categorias/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarCategoria(@PathVariable int id, Model model) {
        categoriasSrvc.eliminarPorId(id);
        return "redirect:/categorias";
    }

}
