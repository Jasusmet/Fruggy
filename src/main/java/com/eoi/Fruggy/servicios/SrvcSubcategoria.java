package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Subcategoria;

import java.util.List;
import java.util.Optional;

public interface SrvcSubcategoria {

    List<Subcategoria> listaSubcategorias();
    Optional<Subcategoria> porIdSubcategoria(int id);
    void guardarSubcategoria(Subcategoria subcategoria);
    void eliminarSubcategoria(int id);


}
