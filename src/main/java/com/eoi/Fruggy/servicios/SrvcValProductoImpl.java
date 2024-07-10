package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ValProducto;
import com.eoi.Fruggy.repositorios.RepoValProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcValProductoImpl implements SrvcValProducto {

    @Autowired
    private RepoValProducto repoValProducto;

    @Override
    public List<ValProducto> listaValProductos() {
        return repoValProducto.findAll();
    }

    @Override
    public Optional<ValProducto> porIdTValoracionProducto(int id) {
        return Optional.empty();
    }

    @Override
    public void guardarValoracionProducto(ValProducto valProducto) {
        repoValProducto.save(valProducto);
    }

    @Override
    public void eliminarValoracionProducto(int id) {
        repoValProducto.deleteById(id);
    }
}
