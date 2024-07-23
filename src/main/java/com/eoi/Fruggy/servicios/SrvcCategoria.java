package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcCategoria extends AbstractSrvc <Categoria, Long, RepoCategoria> {

    @Autowired
    protected SrvcCategoria(RepoCategoria repoCategoria) {
        super(repoCategoria);
    }

}
