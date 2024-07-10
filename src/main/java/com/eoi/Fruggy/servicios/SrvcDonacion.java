package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.repositorios.RepoDonacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcDonacion extends AbstractBusinessSrvc {
    protected SrvcDonacion(RepoDonacion repoDonacion) {
        super(repoDonacion);
    }
}
