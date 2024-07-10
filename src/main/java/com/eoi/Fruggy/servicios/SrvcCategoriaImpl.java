package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SrvcCategoriaImpl implements SrvcCategoria {
    @Autowired
    private RepoCategoria repoCategoria;

    @Override
    public List<Categoria> listaCategorias() {
        return repoCategoria.findAll();
    }

    @Override
    public Optional<Categoria> porIdCategoria(int id) {
        return repoCategoria.findById(id);
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        repoCategoria.save(categoria);
    }

    @Override
    public void eliminarCategoria(int id) {
        repoCategoria.deleteById(id);
    }
}
