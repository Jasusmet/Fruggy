package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import com.eoi.Fruggy.servicios.IUsuarioSrvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppUsuariosSeguridadController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUsuarioSrvc usuarioSrvc;
    private final RepoUsuario repoUsuario;
    private final RepoRol repoRol;

    public AppUsuariosSeguridadController(RepoUsuario repoUsuario, RepoRol repoRol) {
        this.repoUsuario = repoUsuario;
        this.repoRol = repoRol;
    }

    // Read Form data to save into DB

    // Para crear un usuario hay dos bloques:
    // 1. El que genera la pantalla para pedir los datos de tipo GetMapping
    // 2. Cuando pasamos información a la pantalla hay que usar ModelMap

    @GetMapping ("/usuarios/registro")
    public String registro(Model interfazConPantalla) {
        final Usuario usuario = new Usuario();
        final List<Rol> roles = repoRol.findAll();
        interfazConPantalla.addAttribute("usuario", usuario);
        interfazConPantalla.addAttribute("roles", roles);
        System.out.println("Preparando pantalla registro");
        return "crear-usuario";
    }
}
