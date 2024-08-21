package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Supermercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoSupermercado extends JpaRepository<Supermercado, Integer> {

    List<Supermercado> findPais(String pais);
    List<Supermercado> findNombre(String nombre);


}
