package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.repositorios.RepoCesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcCestaImpl implements SrvcCesta {

    @Autowired
    private RepoCesta repoCesta;

    @Override
    public List<Cesta> listaCestas() {
        return repoCesta.findAll();
    }

    @Override
    public Optional<Cesta> porIdCesta(int id) {
        return repoCesta.findById(id);
    }

    @Override
    public void guardarCesta(Cesta cesta) {
        repoCesta.save(cesta);
    }

    @Override
    public void eliminarCesta(int id) {
        repoCesta.deleteById(id);
    }
}
