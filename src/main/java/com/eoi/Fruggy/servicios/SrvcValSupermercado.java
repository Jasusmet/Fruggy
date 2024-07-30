package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcValSupermercado extends AbstractSrvc <ValSupermercado, Long, RepoValSupermercado> {
    protected SrvcValSupermercado(RepoValSupermercado repoValSupermercado) {
        super(repoValSupermercado);
    }

    public List<ValSupermercado> buscarPorSupermercadoId(Long supermercadoId) {
        return getRepo().findBySupermercadoId(supermercadoId);
    }
}
