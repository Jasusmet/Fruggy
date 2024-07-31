package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/usuarios")
public class ADMINDetalleCtrl {

    private final SrvcDetalle detalleSrvc;
    private final SrvcGenero generoSrvc;
    private final SrvcUsuario usuarioSrvc;

    public ADMINDetalleCtrl(SrvcDetalle detalleSrvc, SrvcGenero generoSrvc, SrvcUsuario usuarioSrvc) {
        this.detalleSrvc = detalleSrvc;
        this.generoSrvc = generoSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }

    @GetMapping("/detalles")
    public String crearDetalles(@RequestParam("usuarioId") Long usuarioId, Model model) {
        Optional<Usuario> usuarioOptional = usuarioSrvc.encuentraPorId(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Detalle detalle = new Detalle();
            List<Genero> generos = generoSrvc.buscarEntidades();
            model.addAttribute("detalle", detalle);
            model.addAttribute("usuario", usuario);
            model.addAttribute("generos", generos);
            return "admin/crear-usuario";
        } else {
            model.addAttribute("error", "Usuario no encontrado.");
            return "redirect:/admin/usuarios"; // Redirigir o mostrar un error
        }
    }

    @PostMapping("/detalles")
    public String guardarDetalles(@ModelAttribute("detalle") Detalle detalle, @RequestParam("usuarioId") Long usuarioId, Model model) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioSrvc.encuentraPorId(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            detalle.setUsuario(usuario);
            detalleSrvc.guardar(detalle);
            return "redirect:/admin/usuarios";
        } else {
            model.addAttribute("errorDetalles", "Usuario no encontrado.");
            return "/admin/crear-usuario"; // Vuelve al formulario si hay un error
        }
    }
}


