package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/supermercados")
public class SupermercadoCtrl {

    @Autowired
    private SrvcSupermercado supermercadosSrvc;
    @Autowired
    SrvcUsuario usuarioSrvc;
    @Autowired
    SrvcImagen imagenSrvc;
    @Autowired
    SrvcPrecio precioSrvc;


    @GetMapping
    public String listarSupermercados(Model model) {
        List<Supermercado> supermercados = supermercadosSrvc.buscarEntidades();
        model.addAttribute("supermercados", supermercados);
        return "supermercados";
    }

    @GetMapping("/admin/agregar")
    public String agregarSupermercado(Model model) {
        model.addAttribute("supermercado", new Supermercado());
        return "adminSupermercados";
    }

    @PostMapping
    public String guardarSupermercado(@ModelAttribute Supermercado supermercado, Model model) throws Exception {
        supermercadosSrvc.guardar(supermercado);
        return "redirect:/supermercados";
    }
    @GetMapping("/admin/editar/{id}")
    public String editarSupermercado( @PathVariable("id") Long id, @ModelAttribute Supermercado supermercado, Model model) {
        Optional <Supermercado> supermercadoOptional = supermercadosSrvc.encuentraPorId(id);
        model.addAttribute("supermercado", supermercadoOptional);
        return "adminSupermercados";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminarSupermercado( @PathVariable("id") Long id, Model model) {
        supermercadosSrvc.eliminarPorId(id);
        return "redirect:/supermercados";
    }
}

