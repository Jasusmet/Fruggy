package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValoracionSupermercado;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcValSupermercado extends AbstractSrvc <ValoracionSupermercado, Long, RepoValSupermercado> {
    protected SrvcValSupermercado(RepoValSupermercado repoValSupermercado, RepoValSupermercado repoValSupermercado1) {
        super(repoValSupermercado);
        this.repoValSupermercado = repoValSupermercado1;
    }

    private final RepoValSupermercado repoValSupermercado;

    public List<ValoracionSupermercado> obtenerValoracionesPorSupermercado(Long supermercadoId) {
        return repoValSupermercado.findBySupermercadoId(supermercadoId);
    }

}
