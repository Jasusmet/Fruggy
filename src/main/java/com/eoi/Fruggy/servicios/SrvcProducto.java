package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.repositorios.RepoProducto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcProducto extends AbstractBusinessSrvc {
    protected SrvcProducto(RepoProducto repoProducto) {
        super(repoProducto);
    }
}
