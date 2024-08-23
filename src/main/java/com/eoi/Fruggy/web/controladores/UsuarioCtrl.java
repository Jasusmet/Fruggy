package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcRol;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        return "redirect:/admin/usuarios"; // Redirige a la lista de usuarios
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
            // Manejo de errores
            return "usuarios/editarUsuario";
        }

        Optional<Usuario> usuarioOpt = usuarioSrvc.encuentraPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuarioExistente = usuarioOpt.get();
            // Actualizar otros campos
            Detalle detalle = usuario.getDetalle();
            detalle.setId(usuarioExistente.getDetalle().getId());
            if (detalle.getGenero() != null && detalle.getGenero().getId() != null) {
                Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
                generoOptional.ifPresent(detalle::setGenero);
            }
            detalle = detalleSrvc.merge(detalle);
            usuarioExistente.setDetalle(detalle);
            usuarioSrvc.guardar(usuarioExistente);
            return "redirect:/usuario/administracion";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("usuario/administracion/baja")
    public String darDeBaja(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActive(false);
            usuarioSrvc.guardar(usuario);
            System.out.println("Usuario desactivado: " + usuario.getEmail());
            return "redirect:/logout";
        } else {
            System.out.println("Usuario no encontrado");
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}/cestas")
    public String listarCestas(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioSrvc.encuentraPorId(id);
        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            model.addAttribute("cestas", usuarioOptional.get().getCestas());
            return "usuarios/cestas";
        }
        return "redirect:/usuarios";
    }
}
