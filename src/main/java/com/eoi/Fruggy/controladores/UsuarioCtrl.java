package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioCtrl {

    @Autowired
    private SrvcUsuario usuariosSrvc;

    // Este parámetro sirve para mostrar una lista de los usuarios
    @GetMapping("/usuarios")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String listarUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuariosSrvc.listaUsuarios();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios";
    }

    // Este parámetro sirve para mostrar un usuario buscándolo por su id
    @GetMapping("/usuarios/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String mostrarUsuario(@PathVariable int id, Model model) {
        Optional<Usuario> usuario = usuariosSrvc.porIdUsuario(id);
        model.addAttribute("usuario", usuario);
        return "redirect:/usuarios";
    }

    // Este parámetro sirve para crear un nuevo usuario
    @PostMapping("/usuarios")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String crearUsuario(@RequestBody Usuario usuario, Model model) {
        usuariosSrvc.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Este parámetro sirve para eliminar un usuario
    @DeleteMapping("/usuarios/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String eliminarUsuario(@PathVariable int id, Model model) {
        usuariosSrvc.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

    // Este parámetro te redirige a la pantalla de administrador
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/usuarios/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/usuarios/perfil")
    public String Perfil() {
        return "perfil";
    }

    @GetMapping("/crear-usuario")
    public String CrearUsuario() {
        return "crear-usuario";
    }

    @GetMapping("/recuperar-contraseña")
    public String RecuperarContraseña() {
        return "recuperar-contraseña";
    }

}