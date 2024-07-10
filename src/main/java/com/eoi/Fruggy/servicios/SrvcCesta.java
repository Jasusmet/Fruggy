package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;

import java.util.List;
import java.util.Optional;

public interface SrvcCesta {
    List<Cesta> listaCestas();
    Optional<Cesta> porIdCesta(int id);
    void guardarCesta(Cesta cesta);
    void eliminarCesta(int id);
}
