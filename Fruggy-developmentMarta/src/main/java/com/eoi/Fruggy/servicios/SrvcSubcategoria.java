package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.springframework.stereotype.Service;

@Service
public class SrvcSubcategoria extends AbstractSrvc {
    protected SrvcSubcategoria(RepoSubcategoria repoSubcategoria) {
        super(repoSubcategoria);
    }
}
