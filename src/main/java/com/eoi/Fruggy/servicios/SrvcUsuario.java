package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcUsuario extends AbstractBusinessSrvc {
    protected SrvcUsuario(RepoUsuario repoUsuario) {
        super(repoUsuario);
    }
}
