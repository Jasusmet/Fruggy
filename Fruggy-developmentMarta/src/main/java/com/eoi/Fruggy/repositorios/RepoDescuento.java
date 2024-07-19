package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDescuento extends JpaRepository<Descuento, Integer> {
}
