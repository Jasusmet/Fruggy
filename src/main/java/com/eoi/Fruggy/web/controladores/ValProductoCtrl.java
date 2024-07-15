package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.ValProducto;
import com.eoi.Fruggy.servicios.SrvcValProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ValProductoCtrl {

    @Autowired
    private SrvcValProducto valproductosSrvc;

    @GetMapping("/valproductos")
    public String listarValProductos(Model model) {
        List<ValProducto> listaValProductos = valproductosSrvc.buscarEntidades();
        model.addAttribute("valproductos", listaValProductos);
        return "valproductos";
    }

    @GetMapping("/valproductos/{id}")
    public String mostrarValproducto(@PathVariable int id, Model model) {
        Optional<ValProducto> valproducto = valproductosSrvc.encuentraPorId(id);
        model.addAttribute("valproducto", valproducto);
        return "redirect:/valproductos";
    }

    @PostMapping("/valproductos")
    public String crearValproducto(@RequestBody ValProducto valproducto, Model model) {
        try {
            valproductosSrvc.guardar(valproducto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/valproductos";
    }

    @DeleteMapping("/valproductos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarValproducto(@PathVariable int id, Model model) {
        valproductosSrvc.eliminarPorId(id);
        return "redirect:/valproductos";
    }
}
