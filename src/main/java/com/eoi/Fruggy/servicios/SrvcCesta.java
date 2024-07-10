package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoCesta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcCesta extends AbstractBusinessSrvc {
    protected SrvcCesta(RepoCesta repoCesta) {
        super(repoCesta);
    }
}
