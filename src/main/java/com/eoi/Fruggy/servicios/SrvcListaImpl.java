package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.repositorios.RepoLista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class SrvcListaImpl implements SrvcLista {

    @Autowired
    private RepoLista repoLista;

    @Override
    public List<Lista> listaListas() {
        return repoLista.findAll();
    }

    @Override
    public Optional<Lista> porIdLista(int id) {
        return repoLista.findById(id);
    }

    @Override
    public void guardarLista(Lista lista) {
        repoLista.save(lista);
    }

    @Override
    public void eliminarLista(int id) {
        repoLista.deleteById(id);
    }


}
