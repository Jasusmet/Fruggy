package com.eoi.Fruggy.controladores;


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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/registro")
public class UsuarioCtrl {

    private final SrvcUsuario usuarioSrvc;
    private final SrvcDetalle detalleSrvc;
    private final SrvcRol rolSrvc;
    private final SrvcGenero generoSrvc;
    private final MessageSource messageSource;


    public UsuarioCtrl(SrvcUsuario usuarioSrvc, SrvcDetalle detalleSrvc, SrvcRol rolSrvc, SrvcGenero generoSrvc, MessageSource messageSource) {
        this.usuarioSrvc = usuarioSrvc;
        this.detalleSrvc = detalleSrvc;
        this.rolSrvc = rolSrvc;
        this.generoSrvc = generoSrvc;
        this.messageSource = messageSource;
    }
    @GetMapping()
    public String mostrarFormularioRegistro(Model model) {
        Usuario usuario = new Usuario();
        Detalle detalle = new Detalle();
        detalle.setGenero(new Genero());
        usuario.setDetalle(detalle); // Asegúrate de que el detalle está asociado al usuario
        model.addAttribute("usuario", usuario);
        model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
        return "usuarios/registroUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "registro/crear-usuario";
        }

        try {
            // Obtener el rol de usuario por defecto desde el archivo de propiedades
            Locale locale = new Locale("es");
            String roleName = messageSource.getMessage("role.user", null, locale);
            Optional<Rol> rolUsuarioOpt = rolSrvc.getRepo().findByRolNombre(roleName);
            if (rolUsuarioOpt.isEmpty()) {
                throw new Exception("Rol " + roleName + " no encontrado");
            }
            Rol rolUsuario = rolUsuarioOpt.get();
            usuario.setRoles(Set.of(rolUsuario));
            usuario.setActive(true); // Establecer usuario como activo

            // Asignar el género del detalle
            Detalle detalle = usuario.getDetalle();
            if (detalle.getGenero() != null && detalle.getGenero().getId() != null) {
                Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
                if (generoOptional.isPresent()) {
                    detalle.setGenero(generoOptional.get());
                } else {
                    model.addAttribute("error", "Género no encontrado");
                    model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                    return "registro/crear-usuario";
                }
            } else {
                model.addAttribute("error", "Género no seleccionado");
                model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                return "registro/crear-usuario";
            }

            // Guardar el detalle primero
            detalle = detalleSrvc.guardar(detalle);
            usuario.setDetalle(detalle);

            // Guardar el usuario
            usuarioSrvc.guardar(usuario);
            return "redirect:/login"; // Redirigir al login después de registrar el usuario
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "registro/crear-usuario";
        }
    }

    @GetMapping("/editar")
    public String mostrarFormularioEditar(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/editarUsuario";
        }
        return "redirect:/login";
    }


    // Hay que hacer el logging con un usuario para ver si funciona!!
    @PostMapping("/editar")
    public String editarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult,
                                @AuthenticationPrincipal UserDetails userDetails, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "usuarios/editarUsuario";
        }

        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            Usuario usuarioExistente = usuarioOpt.get();
            Detalle detalle = usuario.getDetalle();
            detalle.setId(usuarioExistente.getDetalle().getId());
            usuarioExistente.setDetalle(detalle);
            detalleSrvc.guardar(detalle);
            usuarioSrvc.guardar(usuarioExistente);
            return "redirect:/registro/editar";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/baja")
    public String darDeBaja(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioSrvc.getRepo().findByEmail(userDetails.getUsername()));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActive(false);
            usuarioSrvc.guardar(usuario);
            return "redirect:/logout";
        } else {
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
