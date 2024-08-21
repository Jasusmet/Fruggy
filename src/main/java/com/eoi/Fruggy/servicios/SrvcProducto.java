package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SrvcProducto extends AbstractSrvc<Producto, Long, RepoProducto> {

    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    RepoUsuario repoUsuario;
    @Autowired
    RepoCesta repoCesta;

    protected SrvcProducto(RepoProducto repoProducto) {
        super(repoProducto);
    }

    public Page<Producto> obtenerProductosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repoProducto.findAll(pageable);
    }
}
