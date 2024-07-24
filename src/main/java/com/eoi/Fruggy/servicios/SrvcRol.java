package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SrvcRol extends AbstractSrvc<Rol, Long, RepoRol> {

    @Autowired
    private RepoRol repoRol;
    @Autowired
    private RepoUsuario repoUsuario;

    protected SrvcRol(RepoRol repoRol) {
        super(repoRol);
    }

    public Rol encontrarPorNombre(String rolNombre) {
        return getRepo().findByRolNombre(rolNombre);
    }

    // Actualizar rol en la base de datos
    @Transactional
    public Rol actualizarRol(Rol rol) {
        return repoRol.save(rol);
    }

    @Override
    public List<Rol> buscarEntidades() {
        List<Rol> roles = super.buscarEntidades();
        System.out.println("Roles encontrados: " + roles.size());
        return roles;
    }

    //Asignar rol a usuario
    public void asignarRolAUsuario(Usuario usuario, Rol rol) {
        Usuario usuarioExistente = repoUsuario.findById(usuario.getId()).orElse(null);
        Rol rolExistente = repoRol.findById(rol.getId()).orElse(null);
        if (usuarioExistente != null && rolExistente != null) {
            usuarioExistente.getRoles().add(rolExistente);
            repoUsuario.save(usuarioExistente);
        }
    }
        //Quitar rol a usuario
        public void quitarRol (Usuario usuario, Rol rol){
            Usuario usuarioExistente = repoUsuario.findById(usuario.getId()).orElse(null);
            Rol rolExistente = repoRol.findById(rol.getId()).orElse(null);
            if (usuarioExistente != null && rolExistente != null) {
                usuarioExistente.getRoles().remove(rolExistente);
                repoUsuario.save(usuarioExistente);
            }

        }
    }
