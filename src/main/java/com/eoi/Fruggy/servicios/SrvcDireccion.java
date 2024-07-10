package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Direccion;

import java.util.List;
import java.util.Optional;

public interface SrvcDireccion {
    List<Direccion> listaDirecciones();
    Optional<Direccion> porIdDirecciones(int id);
    void guardarDirecciones(Direccion direccion);
    void eliminarDirecciones(int id);

}
