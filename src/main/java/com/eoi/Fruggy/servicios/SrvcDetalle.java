package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            throw new IllegalStateException("El nombre de usuario ya está en uso.");
        }
    }

    public Optional<Detalle> findByNombreUsuarioExcludingId(String nombreUsuario, Long id) {
        return repoDetalle.findByNombreUsuarioAndIdNot(nombreUsuario, id);
    }
}
