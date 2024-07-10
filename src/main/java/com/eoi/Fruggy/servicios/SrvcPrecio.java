package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Precio;

import java.util.List;
import java.util.Optional;

public interface SrvcPrecio {

    List<Precio> listaPrecios();

    Optional<Precio> porIdPrecio(int id);

    void guardarPrecio(Precio precio);

    void eliminarPrecio(int id);
}
