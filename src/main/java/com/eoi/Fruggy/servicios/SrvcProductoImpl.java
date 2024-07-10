package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Producto;

import com.eoi.Fruggy.repositorios.RepoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcProductoImpl implements SrvcProducto {

   @Autowired
    private RepoProducto repoProducto;

    @Override
    public List<Producto> listaProductos() {
        return repoProducto.findAll();
    }

    @Override
    public Optional<Producto> porIdProductos(int id) {
        return repoProducto.findById(id);
    }

    @Override
    public void guardarProductos(Producto producto) {
        repoProducto.save(producto);
    }

    @Override
    public void eliminarProductos(int id) {
        repoProducto.deleteById(id);
    }
}
