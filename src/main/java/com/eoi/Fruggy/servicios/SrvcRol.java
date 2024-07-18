package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoRol;
import org.springframework.stereotype.Service;

@Service
public class SrvcRol extends AbstractSrvc<Rol, Long, RepoRol> {
    protected SrvcRol(RepoRol repoRol) {
        super(repoRol);
    }
    public Rol getRol(String rolNombre) {
        return getRepo().findByRolNombre(rolNombre);
    }
}
