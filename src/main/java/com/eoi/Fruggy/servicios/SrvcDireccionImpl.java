package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Direccion;

import com.eoi.Fruggy.repositorios.RepoDireccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcDireccionImpl implements SrvcDireccion {

    @Autowired
    private RepoDireccion repoDireccion;

    @Override
    public List<Direccion> listaDirecciones() {
        return repoDireccion.findAll();
    }

    @Override
    public Optional<Direccion> porIdDirecciones(int id) {
        return repoDireccion.findById(id);
    }

    @Override
    public void guardarDirecciones(Direccion direccion) {
        repoDireccion.save(direccion);
    }

    @Override
    public void eliminarDirecciones(int id) {
        repoDireccion.deleteById(id);
    }

}
