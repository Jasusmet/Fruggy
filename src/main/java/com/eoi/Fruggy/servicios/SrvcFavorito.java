package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Favorito;

import java.util.List;
import java.util.Optional;

public interface SrvcFavorito {

    List<Favorito> listaFavoritos();
    Optional<Favorito> porIdFavorito(int id);
    void guardarFavorito(Favorito favorito);
    void eliminarFavorito(int id);
}
