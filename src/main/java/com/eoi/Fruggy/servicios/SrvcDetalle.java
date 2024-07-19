package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.springframework.stereotype.Service;

@Service
public class SrvcDetalle extends AbstractSrvc  {
    protected SrvcDetalle(RepoDetalle repoDetalle) {
        super(repoDetalle);
    }
}
