package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.ValoracionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoValProducto extends JpaRepository<ValoracionProducto, Long> {
}
