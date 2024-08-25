package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SrvcDetalle extends AbstractSrvc<Detalle, Long, RepoDetalle> {
    @Autowired
    private RepoDetalle repoDetalle;

    protected SrvcDetalle(RepoDetalle repoDetalle) {
        super(repoDetalle);
    }


    public Detalle merge(Detalle detalle) {
        try {
            return repoDetalle.save(detalle);
        } catch (DataIntegrityViolationException e) {
            // Lanzar una excepción personalizada o manejar el error
            throw new IllegalStateException("El nombre de usuario ya está en uso.");
        }
    }
}
