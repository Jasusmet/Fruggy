package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
            model.addAttribute("error", "Invalid username or password.");
        }
        return "/login/login";
    }

    @GetMapping("/login/recuperar-contraseña")
    public String showRecuperarContraseñaForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "An error occurred during password recovery.");
        }
        return "recuperar-contraseña";
    }

    @PostMapping("/login/recuperar-contraseña")
    public String recuperarContraseña(@RequestParam String email, Model model) {
        Optional<Optional<Usuario>> optionalUsuario = Optional.ofNullable(repoUsuario.findByEmail(email));
        if (optionalUsuario.isPresent()) {
            Optional<Usuario> usuario = optionalUsuario.get();
            // Aquí debes implementar el proceso de recuperación de contraseña, por ejemplo, enviar un correo electrónico con un enlace para restablecer la contraseña.
            model.addAttribute("message", "A password reset link has been sent to your email.");
        } else {
            model.addAttribute("error", "No user found with the given email address.");
        }
        return "recuperar-contraseña";
    }
}
