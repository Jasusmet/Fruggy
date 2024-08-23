package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.ValoracionSupermercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoValSupermercado extends JpaRepository<ValoracionSupermercado, Long> {
    List<ValoracionSupermercado> findBySupermercadoId(Long supermercadoId);

    boolean existsBySupermercadoIdAndUsuarioId(Long supermercadoId, Long usuarioId);
}
