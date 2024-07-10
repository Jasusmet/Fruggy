package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoSupermercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcSupermercadoImpl implements SrvcSupermercado {

    @Autowired
    private RepoSupermercado repoSupermercado;

    @Override
    public List<Supermercado> listaSupermercados() {
        return repoSupermercado.findAll();
    }

    @Override
    public Optional<Supermercado> porIdSupermercado(int id) {
        return repoSupermercado.findById(id);
    }

    @Override
    public void guardarSupermercado(Supermercado supermercado) {
        repoSupermercado.save(supermercado);
    }

    @Override
    public void eliminarSupermercado(int id) {
        repoSupermercado.deleteById(id);
    }

}
