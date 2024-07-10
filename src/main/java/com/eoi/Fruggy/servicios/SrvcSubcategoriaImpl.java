package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcSubcategoriaImpl implements SrvcSubcategoria {

    @Autowired
    private RepoSubcategoria repoSubcategoria;

    @Override
    public List<Subcategoria> listaSubcategorias() {
        return repoSubcategoria.findAll();
    }

    @Override
    public Optional<Subcategoria> porIdSubcategoria(int id) {
        return repoSubcategoria.findById(id);
    }

    @Override
    public void guardarSubcategoria(Subcategoria subcategoria) {
        repoSubcategoria.save(subcategoria);
    }

    @Override
    public void eliminarSubcategoria(int id) {
        repoSubcategoria.deleteById(id);
    }



}
