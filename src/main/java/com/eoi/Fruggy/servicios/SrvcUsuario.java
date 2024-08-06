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

public class SrvcUsuario extends AbstractSrvc<Usuario, Long, RepoUsuario> {

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
        return repoUsuario.findById((Long) id);
    }

    @Override
    public Usuario guardar(Usuario usuario) throws Exception {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        // Actualizar los roles si están presentes
        if (usuario.getRoles() != null) {
            usuario.setRoles(new HashSet<>(usuario.getRoles()));
        }
        return super.guardar(usuario);
    }
    public Usuario guardar(Usuario usuario, Set<String> roles) throws Exception {
        Set<Rol> rolesSet = new HashSet<>();
        for (String rolNombre : roles) {
            Optional<Rol> adminRoleopt = repoRol.findByRolNombre(rolNombre);
            if (adminRoleopt.isPresent()) {
                rolesSet.add(adminRoleopt.get());
            }
        }
        usuario.setRoles(rolesSet);
        return guardar(usuario);
    }
    public Set<Rol> obtenerRolesPorUsuario(Long usuarioId) {
        Optional<Usuario> usuario = encuentraPorId(usuarioId);
        return usuario.map(Usuario::getRoles).orElse(new HashSet<>());
    }

    public void eliminarPorId(long id) {
        repoUsuario.deleteById((long) id);
    }

}
