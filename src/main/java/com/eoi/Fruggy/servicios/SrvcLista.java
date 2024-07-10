package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Lista;

import java.util.List;
import java.util.Optional;

public interface SrvcLista {

    List<Lista> listaListas();
    Optional<Lista> porIdLista(int id);
    void guardarLista(Lista lista);
    void eliminarLista(int id);

}
