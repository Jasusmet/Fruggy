package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDonacion extends JpaRepository<Donacion,Integer> {
}
