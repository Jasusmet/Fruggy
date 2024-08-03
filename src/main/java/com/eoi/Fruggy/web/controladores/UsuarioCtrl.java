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
            Optional<Rol> rolUsuarioOpt = Optional.ofNullable(rolSrvc.getRepo().findByRolNombre(roleName));
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

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioSrvc.encuentraPorId(id);
        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
//            model.addAttribute("imagenes", imagenSrvc.buscarEntidadesSet());
            return "admin/modificar-usuario";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "error";
        }
    }
    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "admin/modificar-usuario"; // Regresa a la vista de edición
        }

        Detalle detalle = usuario.getDetalle();
        if (detalle != null && detalle.getGenero() != null && detalle.getGenero().getId() != null) {
            Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
            if (generoOptional.isPresent()) {
                detalle.setGenero(generoOptional.get());
            } else {
                model.addAttribute("error", "Género no encontrado");
                model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
                model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                return "admin/modificar-usuario"; // Regresa a la vista de edición si hay un error
            }
        } else {
            model.addAttribute("error", "Género no seleccionado");
            model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "admin/modificar-usuario"; // Regresa a la vista de edición si hay un error
        }

        // Establecer el ID del usuario para la edición
        usuario.setId(id);
        usuarioSrvc.guardar(usuario);
        return "redirect:/admin/usuarios"; // Redirigir a la lista de usuarios después de guardar
    }
    @GetMapping("/{id}/cestas")
    public String listarCestas(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOptional = usuarioSrvc.encuentraPorId(id);
        if (usuarioOptional.isPresent()) {
            model.addAttribute("usuario", usuarioOptional.get());
            model.addAttribute("cestas", usuarioOptional.get().getCestas());
            return "usuarios/cestas"; // Vista para mostrar las cestas, hay que crear
        }
        return "redirect:/usuarios";
    }
}
