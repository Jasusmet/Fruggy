package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoTipoDescuento;
import org.springframework.stereotype.Service;

@Service
public class SrvcTipodescuento extends AbstractSrvc {
    protected SrvcTipodescuento(RepoTipoDescuento repoTipodescuento) {
        super(repoTipodescuento);
    }
}
