package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValProducto;

import java.util.List;
import java.util.Optional;

public interface SrvcValProducto {

    List<ValProducto> listaValProductos();

    Optional<ValProducto> porIdTValoracionProducto(int id);

    void guardarValoracionProducto(ValProducto valProducto);

    void eliminarValoracionProducto(int id);
}
