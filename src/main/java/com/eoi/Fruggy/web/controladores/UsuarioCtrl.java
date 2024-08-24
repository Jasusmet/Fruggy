package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcRol;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsuarioCtrl {

    private final SrvcUsuario usuarioSrvc;
    private final SrvcDetalle detalleSrvc;
    private final SrvcRol rolSrvc;
    private final SrvcGenero generoSrvc;


    public UsuarioCtrl(SrvcUsuario usuarioSrvc, SrvcDetalle detalleSrvc, SrvcRol rolSrvc, SrvcGenero generoSrvc) {
        this.usuarioSrvc = usuarioSrvc;
        this.detalleSrvc = detalleSrvc;
        this.rolSrvc = rolSrvc;
        this.generoSrvc = generoSrvc;
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        Usuario usuario = new Usuario();
        Detalle detalle = new Detalle();
        detalle.setGenero(new Genero());
        usuario.setDetalle(detalle);
        model.addAttribute("usuario", usuario);
        model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
        model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
        return "usuarios/registroUsuario";
    }

    @PostMapping("/registro/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
                          BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/registroUsuario";
        }

        Detalle detalle = usuario.getDetalle();
        if (detalle != null && detalle.getGenero() != null && detalle.getGenero().getId() != null) {
            Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
            if (generoOptional.isPresent()) {
                detalle.setGenero(generoOptional.get());
            } else {
                model.addAttribute("error", "Género no encontrado");
                model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                return "usuarios/registroUsuario";
            }
        } else {
            model.addAttribute("error", "Género no seleccionado");
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/registroUsuario";
        }

        // Guardar detalle primero
        detalle = detalleSrvc.guardar(detalle);

        // Asignar rol de usuario
        Set<Rol> roles = new HashSet<>();
        Optional<Rol> rolUser = rolSrvc.buscarRolPorNombre("ROLE_USER");
        if (rolUser.isPresent()) {
            roles.add(rolUser.get());
            usuario.setRoles(roles);
        } else {
            model.addAttribute("error", "Rol de usuario no encontrado");
            return "usuarios/registroUsuario";
        }
        usuario.setActive(true);
        usuario.setDetalle(detalle);  // Asegúrate de que se asigna el detalle guardado
        usuarioSrvc.guardar(usuario);

        return "redirect:/login"; // Redirige a login
    }


    @GetMapping("usuario/administracion")
    public String administracionCuenta(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/usuario-administracion-cuenta";
        }
        return "redirect:/login";
    }

    @GetMapping("usuario/administracion/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Usuario> usuarioOpt = usuarioSrvc.encuentraPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verificar si el usuario autenticado es el mismo que está intentando editar
            if (!usuario.getEmail().equals(userDetails.getUsername())) {
                return "redirect:/login"; // Redirigir si no es el mismo usuario
            }

            model.addAttribute("usuario", usuario);
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/editarUsuario";
        }
        return "redirect:/login"; // Si el usuario no se encuentra, redirigir al login
    }

    // Hay que hacer el logging con un usuario para ver si funciona!!
    @PostMapping("usuario/administracion/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
                                BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails,
                                Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/editarUsuario";
        }

        Detalle detalle = usuario.getDetalle();
        if (detalle != null && detalle.getGenero() != null && detalle.getGenero().getId() != null) {
            Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
            if (generoOptional.isPresent()) {
                detalle.setGenero(generoOptional.get());
            } else {
                model.addAttribute("error", "Genero no encontrado");
                model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                return "usuarios/editarUsuario";
            }
        } else {
            model.addAttribute("error", "Genero no seleccionado");
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/editarUsuario";
        }

        usuario.setId(id);
        usuarioSrvc.guardar(usuario);

        return "redirect:/usuario/administracion";
    }

    @PostMapping("usuario/administracion/baja")
    public String darDeBaja(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuarioSrvc.eliminarPorId(usuario.getId());
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    //Al borrar cuenta se va hace un logout y va la pagina de incio.
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/"; // Redirige a la página de inicio después del logout
    }
}
