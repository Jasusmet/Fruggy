package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoSupermercado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcSupermercado extends AbstractBusinessSrvc {
    protected SrvcSupermercado(RepoSupermercado repoSupermercado) {
        super(repoSupermercado);
    }
}