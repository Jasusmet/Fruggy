package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValoracionProducto;
import com.eoi.Fruggy.entidades.ValoracionSupermercado;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcValSupermercado extends AbstractSrvc<ValoracionSupermercado, Long, RepoValSupermercado> {
    protected SrvcValSupermercado(RepoValSupermercado repoValSupermercado, RepoValSupermercado repoValSupermercado1) {
        super(repoValSupermercado);
        this.repoValSupermercado = repoValSupermercado1;
    }

    private final RepoValSupermercado repoValSupermercado;

    public List<ValoracionSupermercado> obtenerValoracionesPorSupermercado(Long supermercadoId) {
        return repoValSupermercado.findBySupermercadoId(supermercadoId);
    }

    public Double calcularNotaMedia(Long supermercadoId) {
        List<ValoracionSupermercado> valoraciones = obtenerValoracionesPorSupermercado(supermercadoId);
        return valoraciones.stream()
                .mapToDouble(ValoracionSupermercado::getNota)
                .average()
                .orElse(0.0);
    }

    public ValoracionSupermercado guardar(ValoracionSupermercado valoracion) {
        // Verificar si ya existe una valoración del usuario para el supermercado
        if (repoValSupermercado.existsBySupermercadoIdAndUsuarioId(
                valoracion.getSupermercado().getId(), valoracion.getUsuario().getId())) {
            throw new RuntimeException("Ya has dejado una valoración para este supermercado.");
        }
        return repoValSupermercado.save(valoracion);
    }
}
