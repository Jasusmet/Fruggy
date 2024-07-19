package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Long>{

    Usuario findByEmail(String email);

}
