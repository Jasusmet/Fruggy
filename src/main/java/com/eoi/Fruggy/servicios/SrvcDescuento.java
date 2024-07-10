package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcDescuento extends AbstractBusinessSrvc {
    protected SrvcDescuento(RepoDescuento repoDescuento) {
        super(repoDescuento);
    }
}
