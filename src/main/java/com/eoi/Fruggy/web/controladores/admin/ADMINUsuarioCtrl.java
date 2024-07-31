package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller()
@RequestMapping("/admin/usuarios")
public class ADMINUsuarioCtrl {

    private static final Logger log = LoggerFactory.getLogger(ADMINUsuarioCtrl.class);
    private final SrvcUsuario usuarioSrvc;
    private final SrvcDetalle detalleSrvc;
    private final SrvcRol rolSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcDonacion donacionSrvc;
    private final SrvcGenero generoSrvc;

    public ADMINUsuarioCtrl(SrvcUsuario usuarioSrvc, SrvcDetalle detalleSrvc, SrvcRol rolSrvc, SrvcImagen imagenSrvc, SrvcDonacion donacionSrvc, SrvcGenero generoSrvc) {
        this.usuarioSrvc = usuarioSrvc;
        this.detalleSrvc = detalleSrvc;
        this.rolSrvc = rolSrvc;
        this.imagenSrvc = imagenSrvc;
        this.donacionSrvc = donacionSrvc;
        this.generoSrvc = generoSrvc;
    }


    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioSrvc.buscarEntidades();
        model.addAttribute("usuarios", usuarios);
        return "admin/CRUD-Usuarios"; // Lo he llamado asi, porque desde esta vista se pueden hacer todas las funciones CRUD como admin de usuarios.
    }

    @GetMapping("/agregar")
    public String agregarUsuario(Model model) {
        Usuario usuario = new Usuario();
        Detalle detalle = new Detalle();
        detalle.setGenero(new Genero());
        usuario.setDetalle(detalle); // Asegúrate de que el detalle está asociado al usuario
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
        model.addAttribute("generos", generoSrvc.buscarEntidadesSet());

       log.info("Usuario agregado. {}", usuario);
        return "admin/crear-usuario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario,
                          BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "admin/crear-usuario";
        }
        // Asignar el género del detalle
        Detalle detalle = usuario.getDetalle();

        // Verificar que detalle y genero no sean null
        if (detalle.getGenero() != null && detalle.getGenero().getId() != null) {
            Optional<Genero> generoOptional = generoSrvc.encuentraPorId(detalle.getGenero().getId());
            if (generoOptional.isPresent()) {
                detalle.setGenero(generoOptional.get());
            } else {
                model.addAttribute("error", "Género no encontrado");
                model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
                model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
                return "admin/crear-usuario";
            }
        } else {
            model.addAttribute("error", "Género no seleccionado");
            model.addAttribute("roles", rolSrvc.buscarEntidadesSet());
            model.addAttribute("generos", generoSrvc.buscarEntidadesSet());
            return "admin/crear-usuario";
        }
        // Guardar el usuario
        usuarioSrvc.guardar(usuario);
        return "redirect:/admin/usuarios";
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
        log.info("Detalle antes de guardar: {}", detalle);
        log.info("Género ID antes de guardar: {}", detalle.getGenero() != null ? detalle.getGenero().getId() : "Género no asignado");

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

    // Mostrar cestas de un usuario
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

