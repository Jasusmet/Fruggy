package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Favorito;
import com.eoi.Fruggy.repositorios.RepoFavorito;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcFavorito extends AbstractBusinessSrvc {
    protected SrvcFavorito(RepoFavorito repoFavorito) {
        super(repoFavorito);
    }
}
