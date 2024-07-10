package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Supermercado;

import java.util.List;
import java.util.Optional;

public interface SrvcSupermercado {

    List<Supermercado> listaSupermercados();

    Optional<Supermercado> porIdSupermercado(int id);

    void guardarSupermercado(Supermercado supermercado);

    void eliminarSupermercado(int id);
}
