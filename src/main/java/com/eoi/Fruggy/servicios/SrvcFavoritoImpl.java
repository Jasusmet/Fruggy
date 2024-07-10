package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Favorito;

import com.eoi.Fruggy.repositorios.RepoFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcFavoritoImpl implements SrvcFavorito {

    @Autowired
    private RepoFavorito repoFavorito;
    @Override
    public List<Favorito> listaFavoritos() {
        return repoFavorito.findAll();
    }

    @Override
    public Optional<Favorito> porIdFavorito(int id) {
        return repoFavorito.findById(id);
    }

    @Override
    public void guardarFavorito(Favorito favorito) {
        repoFavorito.save(favorito);
    }

    @Override
    public void eliminarFavorito(int id) {
        repoFavorito.deleteById(id);
    }
}
