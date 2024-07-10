package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;

import java.util.List;
import java.util.Optional;

public interface SrvcDetalle {
    List<Detalle> listaDetalles();
    Optional<Detalle> porIdDetalle(int id);
    void guardarDetalle(Detalle detalle);
    void eliminarDetalle(int id);
}
