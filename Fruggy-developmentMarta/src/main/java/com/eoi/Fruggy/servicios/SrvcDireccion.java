package com.eoi.Fruggy.servicios;

import org.springframework.stereotype.Service;

@Service
public class SrvcDireccion extends AbstractSrvc {
    protected SrvcDireccion(RepoDireccion repoDireccion) {
        super(repoDireccion);
    }
}
