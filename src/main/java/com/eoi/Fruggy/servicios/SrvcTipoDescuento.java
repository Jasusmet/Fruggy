package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.TipoDescuento;

import java.util.List;
import java.util.Optional;

public interface SrvcTipoDescuento {
    List<TipoDescuento> listaTipoDescuentos();
    Optional<TipoDescuento> porIdTipoDescuento(int id);
    void guardarTipoDescuento(TipoDescuento tipoDescuento);
    void eliminarTipoDescuento(int id);
}
