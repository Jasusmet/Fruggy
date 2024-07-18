package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoPrecio;
import org.springframework.stereotype.Service;

@Service
public class SrvcPrecio extends AbstractSrvc {
    protected SrvcPrecio(RepoPrecio repoPrecio) {
        super(repoPrecio);
    }
}
