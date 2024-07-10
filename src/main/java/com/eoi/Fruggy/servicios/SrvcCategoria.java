package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Categoria;

import java.util.List;
import java.util.Optional;

public interface SrvcCategoria {

    List<Categoria> listaCategorias();

    Optional<Categoria> porIdCategoria(int id);

    void guardarCategoria(Categoria categoria);

    void eliminarCategoria(int id);
}
