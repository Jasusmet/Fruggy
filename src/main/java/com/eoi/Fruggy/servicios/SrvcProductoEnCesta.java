package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.ProductoEnCesta;
import com.eoi.Fruggy.repositorios.RepoProductoEnCesta;
import jakarta.transaction.Transactional;

public class SrvcProductoEnCesta extends AbstractSrvc<ProductoEnCesta, Long, RepoProductoEnCesta>{
    protected SrvcProductoEnCesta(RepoProductoEnCesta repoProductoEnCesta) {
        super(repoProductoEnCesta);
    }
    @Transactional
    public void actualizarCantidad(Long productoEnCestaId, Integer nuevaCantidad) {
        ProductoEnCesta productoEnCesta = getRepo().findById(productoEnCestaId)
                .orElseThrow(() -> new RuntimeException("Producto en cesta no encontrado"));
        productoEnCesta.setCantidad(nuevaCantidad);
        getRepo().save(productoEnCesta);
    }

    @Transactional
    public void eliminarProductoDeCesta(Long productoEnCestaId) {
        getRepo().deleteById(productoEnCestaId);
    }
}
