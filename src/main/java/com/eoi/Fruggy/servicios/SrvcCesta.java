package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoProductoEnCesta;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcCesta extends AbstractSrvc<Cesta, Long, RepoCesta> {

    private final RepoProducto repoProducto;
    private final RepoProductoEnCesta repoProductoEnCesta;

    protected SrvcCesta(RepoCesta repoCesta, RepoProducto repoProducto, RepoProductoEnCesta repoProductoEnCesta) {
        super(repoCesta);
        this.repoProducto = repoProducto;
        this.repoProductoEnCesta = repoProductoEnCesta;
    }

    public List<Cesta> findByUsuario(Usuario usuario) {
        return getRepo().findByUsuario(usuario);
    }

    @Transactional
    public void agregarProductoACesta(Long cestaId, Long productoId, Integer cantidad, String comentario) {
        // Buscar cesta por ID
        Optional<Cesta> cestaOpt = getRepo().findById(cestaId);
        if (cestaOpt.isPresent()) {
            Cesta cesta = cestaOpt.get();
            // Buscar producto por ID
            Producto producto = repoProducto.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Añadir producto a la cesta
            cesta.addProducto(producto, cantidad, comentario);
            getRepo().save(cesta);
        } else {
            throw new RuntimeException("Cesta no encontrada");
        }
    }

    @Transactional
    public void eliminarProductoDeCesta(Long cestaId, Long productoId) {
        // Verificar si la cesta existe
        if (getRepo().existsById(cestaId)) {
            // Eliminar la relación entre la cesta y el producto
            repoProductoEnCesta.deleteByCestaIdAndProductoId(cestaId, productoId);
        } else {
            throw new EntityNotFoundException("Cesta no encontrada");
        }
    }
}