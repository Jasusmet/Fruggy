package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoRol;
import org.springframework.stereotype.Service;

@Service
public class SrvcRol extends AbstractSrvc {
    protected SrvcRol(RepoRol repoRol) {
        super(repoRol);
    }
}
