package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcPrecioImpl implements SrvcPrecio {

    @Autowired
    private RepoPrecio repoPrecio;

    @Override
    public List<Precio> listaPrecios() {
        return repoPrecio.findAll();
    }

    @Override
    public Optional<Precio> porIdPrecio(int id) {
        return repoPrecio.findById(id);
    }

    @Override
    public void guardarPrecio(Precio precio) {
        repoPrecio.save(precio);
    }

    @Override
    public void eliminarPrecio(int id) {
        repoPrecio.deleteById(id);
    }

}
