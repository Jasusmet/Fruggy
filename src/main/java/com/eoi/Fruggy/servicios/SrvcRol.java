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


    @Override
    public List<Rol> buscarEntidades() {
        List<Rol> roles = super.buscarEntidades();
        System.out.println("Roles encontrados: " + roles.size());
        return roles;
    }
}
