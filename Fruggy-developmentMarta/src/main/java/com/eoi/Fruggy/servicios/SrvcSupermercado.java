package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoSupermercado;
import org.springframework.stereotype.Service;

@Service
public class SrvcSupermercado extends AbstractSrvc {
    protected SrvcSupermercado(RepoSupermercado repoSupermercado) {
        super(repoSupermercado);
    }
}