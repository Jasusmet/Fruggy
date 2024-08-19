package com.eoi.Fruggy.controladores.admin;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


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

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarUsuarios(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        Page <Usuario> paginaUsuarios = usuarioSrvc.obtenerUsuariosPaginados(page,size);
        model.addAttribute("usuarios", paginaUsuarios);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaUsuarios.getTotalPages());


        // para comprobar si carga paginas
        log.info("Total de usuarios: {}", paginaUsuarios.getTotalElements());
        log.info("Número total de páginas: {}", paginaUsuarios.getTotalPages());
        log.info("Página actual: {}", page);

        return "admin/CRUD-Usuarios";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
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

    //    @PreAuthorize("hasRole('ADMIN')")
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
        // Crear y asignar el detalle al usuario
        if (detalle != null) {
            detalle = detalleSrvc.guardar(detalle); // Guardar el detalle primero
        } else {
            model.addAttribute("error", "Detalles del usuario no proporcionados");
            return "admin/crear-usuario";
        }

        // Guardar el usuario
        usuarioSrvc.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
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

    //    @PreAuthorize("hasRole('ADMIN')")
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

    //    @PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioSrvc.eliminarPorId(id);
        return "redirect:/admin/usuarios"; // Redirige de vuelta a la lista de usuarios
    }
}

