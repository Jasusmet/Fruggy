package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDireccion extends JpaRepository<Direccion, Integer> {
}
