package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoProducto;
import org.springframework.stereotype.Service;

@Service
public class SrvcProducto extends AbstractSrvc {
    protected SrvcProducto(RepoProducto repoProducto) {
        super(repoProducto);
    }
}
