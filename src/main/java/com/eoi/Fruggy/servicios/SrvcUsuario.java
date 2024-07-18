package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.stereotype.Service;

@Service
public class SrvcUsuario extends AbstractSrvc {
    protected SrvcUsuario(RepoUsuario repoUsuario) {
        super(repoUsuario);
    }
}
