package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoLista;
import org.springframework.stereotype.Service;

@Service
public class SrvcLista extends AbstractSrvc {
    protected SrvcLista(RepoLista repoLista) {
        super(repoLista);
    }
}
