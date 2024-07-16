package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class UsuarioCtrl {

    @Autowired
    private SrvcUsuario usuariosSrvc;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        List<Usuario> listaUsuarios = usuariosSrvc.buscarEntidades();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios";
    }
    @GetMapping("/agregar")
    public String agregar(Usuario usuario,Model model) {
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario,Model model) throws Exception {
        usuariosSrvc.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional usuario = usuariosSrvc.encuentraPorId(id);
        model.addAttribute("usuario", usuario);
        return "modificar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuariosSrvc.eliminarPorId(id);
        return "redirect:/usuarios";
    }
}

