package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.repositorios.RepoGenero;
import org.springframework.stereotype.Service;

@Service
public class SrvcGenero extends AbstractBusinessSrvc {
    protected SrvcGenero(RepoGenero repoGenero) {
        super(repoGenero);
    }
}
