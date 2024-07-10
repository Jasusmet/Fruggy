package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.repositorios.RepoDonacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcDonacionImpl implements SrvcDonacion {

    @Autowired
    private RepoDonacion repoDonacion;

    @Override
    public List<Donacion> listaDonaciones() {
        return repoDonacion.findAll();
    }

    @Override
    public Optional<Donacion> porIdDonaciones(int id) {
        return repoDonacion.findById(id);
    }

    @Override
    public void guardarDonaciones(Donacion donacion) {
        repoDonacion.save(donacion);
    }

    @Override
    public void eliminarDonaciones(int id) {
        repoDonacion.deleteById(id);
    }
}
