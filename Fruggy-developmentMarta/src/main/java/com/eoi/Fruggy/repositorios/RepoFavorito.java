package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoFavorito extends JpaRepository<Favorito, Integer> {
}
