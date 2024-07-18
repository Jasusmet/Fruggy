package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    //  @PreAuthorize("hasRole('ROLE_ADMIN')") //lo quito porque me lleva a la pagina de inicio, hay que ver como poner que cada url te lleva a la pagina que deseas mientras hacer el log in
    public String usuarios(Model model) {
        List<Usuario> listaUsuarios = usuariosSrvc.buscarEntidades();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios";
    }
    @GetMapping("/agregar")
    public String agregar(Usuario usuario, Detalle detalle, Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("detalle", new Detalle());
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario, Model model) throws Exception {
        Detalle detalle = new Detalle();
        detalle.setNombreUsuario(usuario.getEmail());
        usuario.setDetalle(detalle); // Link Detalle con Usuario
        usuariosSrvc.guardar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuario = usuariosSrvc.encuentraPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("detalle", usuario.get().getDetalle());
            return "modificar";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "error";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuariosSrvc.eliminarPorId(id);
        return "redirect:/usuarios";
    }
}

