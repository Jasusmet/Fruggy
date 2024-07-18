package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.repositorios.RepoCesta;
import org.springframework.stereotype.Service;

@Service
public class SrvcCesta extends AbstractSrvc {
    protected SrvcCesta(RepoCesta repoCesta) {
        super(repoCesta);
    }
}
