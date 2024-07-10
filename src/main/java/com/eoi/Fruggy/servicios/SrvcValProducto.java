package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValProducto;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import com.eoi.Fruggy.repositorios.RepoValProducto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcValProducto extends AbstractBusinessSrvc {
    protected SrvcValProducto(RepoValProducto repoValProducto) {
        super(repoValProducto);
    }
}
