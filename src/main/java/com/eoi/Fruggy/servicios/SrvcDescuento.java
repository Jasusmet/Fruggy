package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Descuento;

import java.util.List;
import java.util.Optional;

public interface SrvcDescuento {

    List<Descuento> listaDescuentos();
    Optional<Descuento> porIdDescuento (int id);
    void guardarDescuento(Descuento descuento);
    void eliminarDescuento(int id);
}
