package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPrecio extends JpaRepository<Precio, Integer> {
}
