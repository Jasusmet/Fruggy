package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcRol;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller()
@RequestMapping("/admin/usuarios")
public class ADMINUsuarioCtrl {

    private final SrvcUsuario usuarioSrvc;
    private final SrvcDetalle detalleSrvc;
    private final SrvcRol rolSrvc;
    private final SrvcImagen imagenSrvc;

    public ADMINUsuarioCtrl(SrvcUsuario usuarioSrvc, SrvcDetalle detalleSrvc, SrvcRol rolSrvc, SrvcImagen imagenSrvc) {
        this.usuarioSrvc = usuarioSrvc;
        this.detalleSrvc = detalleSrvc;
        this.rolSrvc = rolSrvc;
        this.imagenSrvc = imagenSrvc;
    }


    @GetMapping
    public String usuariosListarUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioSrvc.buscarEntidades();
        listaUsuarios.forEach(usuario -> {
            Set<Rol> rolesUsuario = usuarioSrvc.obtenerRolesPorUsuario(usuario.getId());
            usuario.setRoles(rolesUsuario);
        });
        model.addAttribute("usuarios", listaUsuarios);
        return "admin/CRUD-Usuarios"; // Lo he llamado asi, porque desde esta vista se pueden hacer todas las funciones CRUD como admin de usuarios.
    }

@GetMapping("/agregar")
public String agregarUsuario(Model model) {
    model.addAttribute("usuario", new Usuario());
    model.addAttribute("detalle", new Detalle());
    List<Rol> roles = rolSrvc.buscarEntidades();
    System.out.println("Roles enviados a la vista: " + roles.size());
    model.addAttribute("roles", roles);
    return "admin/crear-usuario";
}

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario, Detalle detalle,
                          @RequestParam(value = "roles", required = false) List<Long> rolesSeleccionados,
                          Model model) throws Exception {
        // Depuración roles
        System.out.println("Roles seleccionados: " + rolesSeleccionados);
        // Convertir IDs de roles seleccionados en objetos Rol
        Set<Rol> roles = new HashSet<>();
        if (rolesSeleccionados != null) {
            for (Long roleId : rolesSeleccionados) {
                Optional<Rol> rolOpt = rolSrvc.encuentraPorId(roleId);
                if (rolOpt.isPresent()) {
                    roles.add(rolOpt.get());
                } else {
                    System.out.println("Rol con ID " + roleId + " no encontrado.");
                }
            }
        }
        // Imprimir roles asignados al usuario para depuración
        System.out.println("Roles asignados al usuario: " + roles);
        // Asignar roles al usuario
        usuario.setRoles(roles);
        // Asignar detalle al usuario
        usuario.setDetalle(detalle);
        // Guardar o actualizar usuario
        usuarioSrvc.guardar(usuario);
        // Guardar o actualizar detalle
        detalleSrvc.guardar(detalle);
        // Guardar roles actualizados (esto asegura que el nombre del rol se actualice si ha cambiado)
        for (Rol rol : roles) {
            rolSrvc.actualizarRol(rol);
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/asignar-rol")
    public String asignarRol(@RequestParam("usuarioId") Long usuarioId, @RequestParam("rolId") Long rolId) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioSrvc.encuentraPorId(usuarioId);
        Optional<Rol> rolOpt = rolSrvc.encuentraPorId(rolId);
        if (usuarioOpt.isPresent() && rolOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Rol rol = rolOpt.get();
            rolSrvc.asignarRolAUsuario(usuario, rol);
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/quitar-rol")
    public String quitarRol(@RequestParam("usuarioId") Long usuarioId, @RequestParam("rolId") Long rolId) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioSrvc.encuentraPorId(usuarioId);
        Optional<Rol> rolOpt = rolSrvc.encuentraPorId(rolId);

        if (usuarioOpt.isPresent() && rolOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Rol rol = rolOpt.get();
            rolSrvc.quitarRol(usuario, rol);
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuario = usuarioSrvc.encuentraPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            List<Rol> roles = rolSrvc.buscarEntidades();
            model.addAttribute("roles", roles);
            return "admin/modificar-usuario"; // vista para editar
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "error";
        }
    }
    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable("id") long id, @ModelAttribute Usuario usuario) throws Exception {
        usuario.setId(id);
        usuarioSrvc.guardar(usuario);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioSrvc.eliminarPorId(id);
        return "redirect:/admin/usuarios";
    }
}

