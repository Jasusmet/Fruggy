package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcValSupermercadoImpl implements SrvcValSupermercado {

    @Autowired
    private RepoValSupermercado repoValSupermercado;


    @Override
    public List<ValSupermercado> listaValSupermercados() {
        return repoValSupermercado.findAll();
    }

    @Override
    public Optional<ValSupermercado> porIdValoracionSupermercado(int id) {
        return repoValSupermercado.findById(id);
    }

    @Override
    public void guardarValoracionSupermercado(ValSupermercado valSupermercado) {
        repoValSupermercado.save(valSupermercado);
    }

    @Override
    public void eliminarValoracionSupermercado(int id) {
        repoValSupermercado.deleteById(id);
    }

}
