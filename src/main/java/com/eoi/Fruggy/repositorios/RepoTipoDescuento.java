package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoTipoDescuento extends JpaRepository<TipoDescuento, Long> {
    TipoDescuento findByTipo(String tipo);
}
