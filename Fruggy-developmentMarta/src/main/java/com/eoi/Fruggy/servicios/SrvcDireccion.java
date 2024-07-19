package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoDireccion;
import org.springframework.stereotype.Service;

@Service
public class SrvcDireccion extends AbstractSrvc {
    protected SrvcDireccion(RepoDireccion repoDireccion) {
        super(repoDireccion);
    }
}
