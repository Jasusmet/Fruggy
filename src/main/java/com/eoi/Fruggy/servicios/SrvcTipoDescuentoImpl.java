package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.repositorios.RepoTipoDescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcTipoDescuentoImpl implements SrvcTipoDescuento {

    @Autowired
    private RepoTipoDescuento repoTipoDescuento;

    @Override
    public List<TipoDescuento> listaTipoDescuentos() {
        return repoTipoDescuento.findAll();
    }

    @Override
    public Optional<TipoDescuento> porIdTipoDescuento(int id) {
        return repoTipoDescuento.findById(id);
    }

    @Override
    public void guardarTipoDescuento(TipoDescuento tipoDescuento) {
        repoTipoDescuento.save(tipoDescuento);
    }

    @Override
    public void eliminarTipoDescuento(int id) {
        repoTipoDescuento.deleteById(id);
    }

}
