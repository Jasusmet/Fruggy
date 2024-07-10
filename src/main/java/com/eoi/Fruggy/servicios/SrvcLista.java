package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.repositorios.RepoLista;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcLista extends AbstractBusinessSrvc {
    protected SrvcLista(RepoLista repoLista) {
        super(repoLista);
    }
}
