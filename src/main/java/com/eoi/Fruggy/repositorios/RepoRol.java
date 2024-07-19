package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRol extends JpaRepository<Rol, Long> {
    Rol findByRolNombre(String rolNombre);
}
