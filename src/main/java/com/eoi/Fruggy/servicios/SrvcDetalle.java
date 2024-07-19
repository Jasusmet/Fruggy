package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SrvcDetalle extends AbstractSrvc<Detalle, Long, RepoDetalle> {

    @Autowired
    private RepoDetalle repoDetalle;

    protected SrvcDetalle(RepoDetalle repoDetalle) {
        super(repoDetalle);
    }
    public Detalle findById(Long id) {
        return repoDetalle.findById(id).orElse(null);
    }

}
