package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginCtrl {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RepoUsuario repoUsuario;

    public LoginCtrl(BCryptPasswordEncoder bCryptPasswordEncoder, RepoUsuario repoUsuario) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.repoUsuario = repoUsuario;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "/login/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> optionalUsuario = Optional.ofNullable(repoUsuario.findByEmail(email));
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            System.out.println("Usuario encontrado: " + usuario.getEmail());
            System.out.println("Contraseña del usuario: " + usuario.getPassword());
            if (bCryptPasswordEncoder.matches(password, usuario.getPassword())) {
                return "redirect:/"; // Redirige a la página principal
            } else {
                model.addAttribute("error", true); // Contraseña incorrecta
                System.out.println("Contraseña incorrecta para el usuario: " + usuario.getEmail());
            }
        } else {
            model.addAttribute("error", true); // Usuario no encontrado
            System.out.println("Usuario no encontrado: " + email);
        }
        return "/login/login"; // Vuelve a la vista de login sin redirigir
    }

    @GetMapping("/login/recuperar-contraseña")
    public String showRecuperarContraseñaForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "recuperar-contraseña";
    }
}
