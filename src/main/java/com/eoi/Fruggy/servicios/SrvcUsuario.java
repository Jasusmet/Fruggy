package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SrvcUsuario extends AbstractSrvc {

    private final RepoUsuario repoUsuario;
    private final RepoRol repoRol;
    private final PasswordEncoder passwordEncoder;


    // Se añaden estos métodos para agregar Bcrypt a contraseña cuando se cree nuevo usuario.
    @Autowired
    public SrvcUsuario(RepoUsuario repoUsuario, RepoRol repoRol, PasswordEncoder passwordEncoder) {
        super(repoUsuario);
        this.repoUsuario = repoUsuario;
        this.repoRol = repoRol;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> buscarEntidades() {
        return repoUsuario.findAll();
    }

    public Optional<Usuario> encuentraPorId(long id) {
        return repoUsuario.findById((int) id);
    }

    public void guardar(Usuario usuario, Set<String> roles) {
        // Encriptar la contraseña antes de asignarla al usuario
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Asignar roles al usuario antes de guardar
        Set<Rol> usuarioRoles = new HashSet<>();
        if (roles == null || roles.isEmpty()) {
            // Si no se especifican roles, asignar el rol de usuario por defecto
            Rol userRole = repoRol.findByRolNombre("ROLE_USER");
            if (userRole != null) {
                usuarioRoles.add(userRole);
            }
        } else {
            for (String rolNombre : roles) {
                Rol rol = repoRol.findByRolNombre(rolNombre);
                if (rol != null) {
                    usuarioRoles.add(rol);
                }
            }
        }
        usuario.setRoles(usuarioRoles);
        // Guardar los detalles del usuario si existen
        if (usuario.getDetalle() != null) {
            usuario.getDetalle().setUsuario(usuario); // Asegurar la relación bidireccional
        }
        repoUsuario.save(usuario); // Guardar usuario junto con roles y detalles
    }

    public void eliminarPorId(long id) {
        repoUsuario.deleteById((int) id);
    }

    public Set<Rol> obtenerRolesPorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOpt = repoUsuario.findById(Math.toIntExact(usuarioId));
        return usuarioOpt.map(Usuario::getRoles).orElse(Collections.emptySet());
    }

}
