package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoDonacion;
import org.springframework.stereotype.Service;

@Service
public class SrvcDonacion extends AbstractSrvc {
    protected SrvcDonacion(RepoDonacion repoDonacion) {
        super(repoDonacion);
    }
}
