package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvcCesta extends AbstractSrvc<Cesta, Long, RepoCesta> {
    protected SrvcCesta(RepoCesta repoCesta) {
        super(repoCesta);

    }
    public List<Cesta> findByUsuario(Usuario usuario) {
        return getRepo().findByUsuario(usuario);
    }
}
