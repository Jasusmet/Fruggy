package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.ValSupermercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoValSupermercado extends JpaRepository<ValSupermercado, Integer> {
}
