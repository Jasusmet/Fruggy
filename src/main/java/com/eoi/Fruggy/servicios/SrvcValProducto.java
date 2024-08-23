package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValoracionProducto;
import com.eoi.Fruggy.repositorios.RepoValProducto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcValProducto extends AbstractSrvc<ValoracionProducto, Long, RepoValProducto> {

    private final RepoValProducto repoValProducto;

    protected SrvcValProducto(RepoValProducto repoValProducto, RepoValProducto repo) {
        super(repoValProducto);
        this.repoValProducto = repoValProducto;
    }
    public List<ValoracionProducto> obtenerValoracionesPorProducto(Long productoId) {
        return repoValProducto.findByProductoId(productoId);
    }

    public double calcularNotaMedia(Long productoId) {
        List<ValoracionProducto> valoraciones = obtenerValoracionesPorProducto(productoId);
        return valoraciones.stream()
                .mapToDouble(ValoracionProducto::getNota)
                .average()
                .orElse(0);
    }
    public ValoracionProducto guardar(ValoracionProducto valoracion) {
        // Verificar si ya existe una valoración del usuario para el producto
        if (getRepo().existsByUsuarioAndProducto(valoracion.getUsuario(), valoracion.getProducto())) {
            throw new RuntimeException("Ya has dejado una valoración para este producto.");
        }
        return getRepo().save(valoracion);
    }


}
