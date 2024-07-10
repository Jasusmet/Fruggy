package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface SrvcRol {

    List<Rol> listaRole();
    Optional<Rol> porIdRole(int id);
    void guardarRole(Rol rol);
    void eliminarRole(int id);
}
