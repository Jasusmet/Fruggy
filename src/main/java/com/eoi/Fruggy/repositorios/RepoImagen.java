package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoImagen extends JpaRepository<Imagen, Long> {
}
