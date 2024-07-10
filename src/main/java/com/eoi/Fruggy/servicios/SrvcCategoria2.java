package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoCategoria;
import org.springframework.stereotype.Service;

@Service
public class SrvcCategoria2 extends AbstractBusinessSrvc{
    protected SrvcCategoria2(RepoCategoria repoCategoria) {
        super(repoCategoria);
    }
}
