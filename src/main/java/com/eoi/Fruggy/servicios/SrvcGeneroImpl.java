package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.repositorios.RepoGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class SrvcGeneroImpl implements SrvcGenero {

    @Autowired
    private RepoGenero repoGenero;
    @Override
    public void guardarGenero(Genero genero) {
        repoGenero.save(genero);
    }

    @Override
    public void eliminarGenero(int id) {
        repoGenero.deleteById(id);
    }

}
