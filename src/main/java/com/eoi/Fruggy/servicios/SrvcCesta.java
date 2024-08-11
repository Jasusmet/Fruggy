package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcCesta extends AbstractSrvc<Cesta, Long, RepoCesta> {
    protected SrvcCesta(RepoCesta repoCesta) {
        super(repoCesta);

    }

    public List<Cesta> findByUsuario(Usuario usuario) {
        return getRepo().findByUsuario(usuario);
    }

    @Transactional
    public void agregarProductoACesta(Long cestaId, Producto producto) {
        Optional<Cesta> cestaOptional = getRepo().findById(cestaId);
        if (cestaOptional.isPresent()) {
            Cesta cesta = cestaOptional.get();
            cesta.getProductos().add(producto);
            getRepo().save(cesta);
        } else {
            throw new RuntimeException("Cesta no encontrada");
        }
    }
}