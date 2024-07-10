package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcRolImpl implements SrvcRol {

    @Autowired
    private RepoRol repoRol;

    @Override
    public List<Rol> listaRole() {
        return repoRol.findAll();
    }

    @Override
    public Optional<Rol> porIdRole(int id) {
        return repoRol.findById(id);
    }

    @Override
    public void guardarRole(Rol rol) {
        repoRol.save(rol);
    }

    @Override
    public void eliminarRole(int id) {
        repoRol.deleteById(id);
    }

}
