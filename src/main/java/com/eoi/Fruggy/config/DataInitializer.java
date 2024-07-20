package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoGenero;
import com.eoi.Fruggy.repositorios.RepoRol;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class DataInitializer {
    @Autowired
    private RepoRol repoRol;
    @Autowired
    private RepoGenero repoGenero;

    @PostConstruct
    public void init() {
        //  roles
        if (repoRol.count() == 0) {
            repoRol.save(new Rol("admin"));
            repoRol.save(new Rol("user"));
        }

        //  géneros
        if (repoGenero.count() == 0) {
            repoGenero.save(new Genero("Masculino"));
            repoGenero.save(new Genero("Femenino"));
            repoGenero.save(new Genero("Otros"));
        }
    }
}
