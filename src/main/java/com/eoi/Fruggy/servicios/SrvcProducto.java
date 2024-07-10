package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Producto;

import java.util.List;
import java.util.Optional;

public interface SrvcProducto {

    List<Producto> listaProductos();

    Optional<Producto> porIdProductos(int id);

    void guardarProductos(Producto producto);

    void eliminarProductos(int id);
}
