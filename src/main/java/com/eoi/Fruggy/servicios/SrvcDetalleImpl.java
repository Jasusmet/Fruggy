package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcDetalleImpl implements SrvcDetalle {

    @Autowired
    private RepoDetalle repoDetalle;

    @Override
    public List<Detalle> listaDetalles() {
        return repoDetalle.findAll();
    }

    @Override
    public Optional<Detalle> porIdDetalle(int id) {
        return repoDetalle.findById(id);
    }

    @Override
    public void guardarDetalle(Detalle detalle) {
        repoDetalle.save(detalle);
    }

    @Override
    public void eliminarDetalle(int id) {
        repoDetalle.deleteById(id);
    }

}
