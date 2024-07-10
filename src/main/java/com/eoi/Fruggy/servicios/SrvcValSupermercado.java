package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcValSupermercado extends AbstractBusinessSrvc {
    protected SrvcValSupermercado(RepoValSupermercado repoValSupermercado) {
        super(repoValSupermercado);
    }
}
