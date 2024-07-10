package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcSubcategoria extends AbstractBusinessSrvc {
    protected SrvcSubcategoria(RepoSubcategoria repoSubcategoria) {
        super(repoSubcategoria);
    }
}
