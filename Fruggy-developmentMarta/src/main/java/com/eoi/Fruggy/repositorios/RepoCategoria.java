package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCategoria extends JpaRepository<Categoria, Integer> {
}
