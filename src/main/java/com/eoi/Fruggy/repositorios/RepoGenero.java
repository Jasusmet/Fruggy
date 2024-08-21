package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoGenero extends JpaRepository<Genero, Integer> {

    Optional<Genero> findById(Long id);

}
