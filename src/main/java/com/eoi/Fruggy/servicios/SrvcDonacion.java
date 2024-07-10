package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Donacion;

import java.util.List;
import java.util.Optional;

public interface SrvcDonacion {
    List<Donacion> listaDonaciones();

    Optional<Donacion> porIdDonaciones(int id);

    void guardarDonaciones(Donacion donacion);

    void eliminarDonaciones(int id);
}
