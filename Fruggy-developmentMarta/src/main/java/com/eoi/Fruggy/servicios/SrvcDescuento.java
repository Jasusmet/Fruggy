package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoDescuento;
import org.springframework.stereotype.Service;

@Service
public class SrvcDescuento extends AbstractSrvc {
    protected SrvcDescuento(RepoDescuento repoDescuento) {
        super(repoDescuento);
    }
}
