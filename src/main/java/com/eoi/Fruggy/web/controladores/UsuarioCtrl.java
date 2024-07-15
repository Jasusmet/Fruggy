package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listarUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuariosSrvc.buscarEntidades();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios";
    }

    // Este parámetro sirve para mostrar un usuario buscándolo por su id
    @GetMapping("/usuarios/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String mostrarUsuario(@PathVariable int id, Model model) {
        Optional<Usuario> usuario = usuariosSrvc.encuentraPorId(id);
        model.addAttribute("usuario", usuario);
        return "redirect:/usuarios";
    }

    // Este parámetro sirve para crear un nuevo usuario
    @PostMapping("/usuarios/sign-up")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public String crearUsuario(@RequestBody Usuario usuario, Model model) {
        try {
            usuariosSrvc.guardar(usuario);
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear usuario: " + e.getMessage());
            return "error"; // añadir una pagina de error.html
        }
        return "redirect:/usuarios";
    }

    // Este parámetro sirve para eliminar un usuario
    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String eliminarUsuario(@PathVariable int id, Model model) {
        usuariosSrvc.eliminarPorId(id);
        return "redirect:/usuarios";
    }

    // Este parámetro te redirige a la pantalla de administrador
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/usuarios/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/usuarios/perfil")
    public String Perfil() {
        return "perfil";
    }

    @GetMapping("/crear-usuario")
    public String crearUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crear-usuario";
    }

    @GetMapping("/recuperar-contraseña")
    public String recuperarContraseña() {
        return "recuperar-contraseña";
    }

}