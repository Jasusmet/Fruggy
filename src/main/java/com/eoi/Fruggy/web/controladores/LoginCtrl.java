package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
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
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<Usuario> optionalUsuario = Optional.ofNullable(repoUsuario.findByEmail(email));
        if (optionalUsuario.isPresent() && optionalUsuario.get().getPassword().equals(bCryptPasswordEncoder.encode(password))) {
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            model.addAttribute("msg", "Usuario encontrado");
            return "/";
        } else {
            model.addAttribute("msg", "Usuario no encontrado");
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/login/crear-usuario")
    public String showCrearUsuarioForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "adminUsuarios";
    }

    @GetMapping("/login/recuperar-contraseña")
    public String showRecuperarContraseñaForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "recuperar-contraseña";
    }

}
