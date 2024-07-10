package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Direccion;
import com.eoi.Fruggy.repositorios.RepoDireccion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcDireccion extends AbstractBusinessSrvc {
    protected SrvcDireccion(RepoDireccion repoDireccion) {
        super(repoDireccion);
    }
}
