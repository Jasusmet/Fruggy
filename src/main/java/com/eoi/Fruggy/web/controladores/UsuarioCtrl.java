package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
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


@Controller
@Slf4j
public class UsuarioCtrl {

    @Autowired
    private SrvcUsuario usuariosSrvc;
    @Autowired
    private SrvcRol srvcRol;

    @GetMapping("/usuarios")
    //  @PreAuthorize("hasRole('ROLE_ADMIN')") //lo quito porque me lleva a la pagina de inicio, hay que ver como poner que cada url te lleva a la pagina que deseas mientras hacer el log in
    public String usuarios(Model model) {
        List<Usuario> listaUsuarios = usuariosSrvc.buscarEntidades();
        // Cargar los roles de cada usuario
        listaUsuarios.forEach(usuario -> {
            Set<Rol> rolesUsuario = usuariosSrvc.obtenerRolesPorUsuario(usuario.getId());
            usuario.setRoles(rolesUsuario);
        });
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios";
    }

    @GetMapping("/agregar")
    public String agregar(Usuario usuario, Detalle detalle, Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("detalle", new Detalle());
        // Obtener la lista de roles y añadir al modelo
        List<Rol> roles = srvcRol.buscarEntidades();
        model.addAttribute("roles", roles);
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
                          @RequestParam(value = "rolesSeleccionados", required = false) List<Long> rolesSeleccionados,
                          Model model) throws Exception {
        // Obtener roles seleccionados y asignar al usuario
        Set<String> roles = new HashSet<>();
        if (rolesSeleccionados != null) {
            for (Long roleId : rolesSeleccionados) {
                Optional<Rol> rolOpt = srvcRol.encuentraPorId(roleId);
                rolOpt.ifPresent(rol -> roles.add(rol.getRolNombre()));
            }
        }
        // Guardar el usuario y asegurarse de que se persistan los roles seleccionados
        usuariosSrvc.guardar(usuario, roles);
        return "redirect:/usuarios";
    }

    @PostMapping("/asignar-rol")
    public String asignarRol(@RequestParam("usuarioId") Long usuarioId, @RequestParam("rolId") Long rolId) throws Exception {
        Optional<Usuario> usuarioOpt = usuariosSrvc.encuentraPorId(usuarioId);
        Optional<Rol> rolOpt = srvcRol.encuentraPorId(rolId);

        if (usuarioOpt.isPresent() && rolOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Rol rol = rolOpt.get();
            usuario.getRoles().add(rol);
            Set<String> roles = new HashSet<>();
            roles.add(rol.getRolNombre());
            usuariosSrvc.guardar(usuario, roles);
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional<Usuario> usuario = usuariosSrvc.encuentraPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            model.addAttribute("detalle", usuario.get().getDetalle());
            List<Rol> roles = srvcRol.buscarEntidades();
            model.addAttribute("roles", roles);
            return "modificar";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "error";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuariosSrvc.eliminarPorId(id);
        return "redirect:/usuarios";
    }
}

