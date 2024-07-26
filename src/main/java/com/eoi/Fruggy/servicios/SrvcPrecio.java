package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcPrecio extends AbstractSrvc {

    protected SrvcPrecio(RepoPrecio repoPrecio) {
        super(repoPrecio);
    }
}
