package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoLista extends JpaRepository<Lista, Integer> {
}
