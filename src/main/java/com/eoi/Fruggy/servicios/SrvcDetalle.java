package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcDetalle extends AbstractBusinessSrvc {
    protected SrvcDetalle(RepoDetalle repoDetalle) {
        super(repoDetalle);
    }
}
