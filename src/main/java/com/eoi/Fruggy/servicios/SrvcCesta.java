package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import com.eoi.Fruggy.repositorios.RepoProducto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcCesta extends AbstractSrvc<Cesta, Long, RepoCesta> {

    private final RepoProducto repoProducto;

    protected SrvcCesta(RepoCesta repoCesta, RepoProducto repoProducto) {
        super(repoCesta);
        this.repoProducto = repoProducto;
    }

    public List<Cesta> findByUsuario(Usuario usuario) {
        return getRepo().findByUsuario(usuario);
    }

    @Transactional
    public void agregarProductoACesta(Long cestaId, Long productoId) {
        Optional<Cesta> cestaOptional = getRepo().findById(cestaId);
        Optional<Producto> productoOptional = repoProducto.findById(productoId);

        if (cestaOptional.isPresent() && productoOptional.isPresent()) {
            Cesta cesta = cestaOptional.get();
            Producto producto = productoOptional.get();

            cesta.getProductos().add(producto);
            producto.getCestas().add(cesta); // Asegúrate de mantener la relación bidireccional

            getRepo().save(cesta);
            repoProducto.save(producto);
        } else {
            throw new RuntimeException("Cesta o producto no encontrado");
        }
    }
}