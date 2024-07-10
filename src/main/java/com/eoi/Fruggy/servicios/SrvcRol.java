package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoRol;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcRol extends AbstractBusinessSrvc {
    protected SrvcRol(RepoRol repoRol) {
        super(repoRol);
    }
}
