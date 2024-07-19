package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoRol;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    private RepoRol repoRol;

    @PostConstruct
    public void init() {
        if (repoRol.count() == 0) {
            repoRol.save(new Rol("admin"));
            repoRol.save(new Rol("user"));
        }
    }
}
