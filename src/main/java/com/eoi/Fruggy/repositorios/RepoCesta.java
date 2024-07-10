package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Cesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCesta extends JpaRepository<Cesta, Integer> {
}
