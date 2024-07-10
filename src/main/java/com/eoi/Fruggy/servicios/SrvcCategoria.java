package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoCategoria;
import org.springframework.stereotype.Service;

@Service
public class SrvcCategoria extends AbstractBusinessSrvc {
    protected SrvcCategoria(RepoCategoria repoCategoria) {
        super(repoCategoria);
    }
}
