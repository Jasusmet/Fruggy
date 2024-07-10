package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Descuento;

import com.eoi.Fruggy.repositorios.RepoDescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcDescuentoImpl implements SrvcDescuento{

    @Autowired
    private RepoDescuento repoDescuento;

    @Override
    public List<Descuento> listaDescuentos() {
        return repoDescuento.findAll();
    }

    @Override
    public Optional<Descuento> porIdDescuento(int id) {
        return repoDescuento.findById(id);
    }

    @Override
    public void guardarDescuento(Descuento descuento) {
        repoDescuento.save(descuento);
    }

    @Override
    public void eliminarDescuento(int id) {
        repoDescuento.deleteById(id);
    }
}
