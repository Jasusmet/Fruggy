package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoFavorito;
import org.springframework.stereotype.Service;

@Service
public class SrvcFavorito extends AbstractSrvc {
    protected SrvcFavorito(RepoFavorito repoFavorito) {
        super(repoFavorito);
    }
}
