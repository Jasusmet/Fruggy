package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDetalle extends JpaRepository<Detalle, Long> {
}
