package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPrecio extends JpaRepository<Precio, Integer> {
}
