package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.*;
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
        // Buscar la cesta por ID
        Cesta cesta = getRepo().findById(cestaId)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        // Buscar el producto por ID
        Producto producto = repoProducto.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar si el producto ya está en la cesta (dentro de ProductoEnCesta)
        Optional<ProductoEnCesta> productoEnCestaOpt = cesta.getProductosEnCesta().stream()
                .filter(pc -> pc.getProducto().getId().equals(productoId))
                .findFirst();

        if (productoEnCestaOpt.isPresent()) {
            ProductoEnCesta productoEnCesta = productoEnCestaOpt.get();
            // Si el producto ya existe en la cesta, actualizar la cantidad
            int nuevaCantidad = productoEnCesta.getCantidad() + cantidad;
            if (nuevaCantidad > 0) {
                productoEnCesta.setCantidad(nuevaCantidad);
            } else {
                // Si la cantidad se reduce a cero o menos, eliminar el producto de la cesta
                cesta.getProductosEnCesta().remove(productoEnCesta);
                repoProductoEnCesta.delete(productoEnCesta);
            }
        } else {
            // Si el producto no está en la cesta, agregarlo
            if (cantidad > 0) {
                ProductoEnCesta productoEnCesta = new ProductoEnCesta();
                productoEnCesta.setCesta(cesta);
                productoEnCesta.setProducto(producto);
                productoEnCesta.setCantidad(cantidad);
                productoEnCesta.setComentario(comentario);

                cesta.getProductosEnCesta().add(productoEnCesta);
            } else {
                throw new RuntimeException("La cantidad debe ser mayor que cero para agregar un producto a la cesta.");
            }
        }
    }

    @Transactional
    public void eliminarProductoDeCesta(Long cestaId, Long productoId) {
        Cesta cesta = getRepo().findById(cestaId)
                .orElseThrow(() -> new EntityNotFoundException("Cesta no encontrada"));

        ProductoEnCesta productoEnCesta = repoProductoEnCesta.findByCestaIdAndProductoId(cestaId, productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la cesta"));

        cesta.getProductosEnCesta().remove(productoEnCesta);
        repoProductoEnCesta.delete(productoEnCesta); // Eliminar de la base de datos

        getRepo().save(cesta);
    }

    @Transactional
    public void actualizarCantidadProductoEnCesta(Long cestaId, Long productoId, Integer cantidad) {
        Cesta cesta = encuentraPorId(cestaId)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        ProductoEnCesta productoEnCesta = cesta.getProductosEnCesta().stream()
                .filter(pc -> pc.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en la cesta"));

        if (cantidad <= 0) {
            // Eliminar el producto si la cantidad es cero o negativa
            cesta.getProductosEnCesta().remove(productoEnCesta);
            repoProductoEnCesta.delete(productoEnCesta);
        } else {
            // Actualizar la cantidad del producto en la cesta
            productoEnCesta.setCantidad(cantidad);
        }
        getRepo().save(cesta);
    }
}

