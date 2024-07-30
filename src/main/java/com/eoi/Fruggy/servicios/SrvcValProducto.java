package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValProducto;
import com.eoi.Fruggy.repositorios.RepoValProducto;
import org.springframework.stereotype.Service;

@Service
public class SrvcValProducto extends AbstractSrvc<ValProducto, Long, RepoValProducto> {
    protected SrvcValProducto(RepoValProducto repoValProducto) {
        super(repoValProducto);
    }
}
