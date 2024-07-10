package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoTipoDescuento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcTipodescuento extends AbstractBusinessSrvc {
    protected SrvcTipodescuento(RepoTipoDescuento repoTipodescuento) {
        super(repoTipodescuento);
    }
}
