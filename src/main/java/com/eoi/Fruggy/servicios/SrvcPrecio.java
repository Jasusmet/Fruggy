package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcPrecio extends AbstractBusinessSrvc {
    protected SrvcPrecio(RepoPrecio repoPrecio) {
        super(repoPrecio);
    }
}
