package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.stereotype.Service;

@Service
public class SrvcValSupermercado extends AbstractSrvc {
    protected SrvcValSupermercado(RepoValSupermercado repoValSupermercado) {
        super(repoValSupermercado);
    }
}
