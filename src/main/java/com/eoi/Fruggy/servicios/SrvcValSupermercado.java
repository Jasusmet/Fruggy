package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValSupermercado;

import java.util.List;
import java.util.Optional;

public interface SrvcValSupermercado {

    List<ValSupermercado> listaValSupermercados();
    Optional<ValSupermercado> porIdValoracionSupermercado(int id);
    void guardarValoracionSupermercado(ValSupermercado valSupermercado);
    void eliminarValoracionSupermercado(int id);
}
