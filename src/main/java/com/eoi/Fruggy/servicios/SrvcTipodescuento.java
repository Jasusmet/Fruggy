package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoTipoDescuento;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SrvcTipodescuento extends AbstractSrvc {

    private final RepoTipoDescuento repoTipoDescuento;
    private final RepoUsuario repoUsuario;
    private final RepoProducto repoProducto;

    protected SrvcTipodescuento(RepoTipoDescuento repoTipodescuento, RepoTipoDescuento repoTipoDescuento, RepoUsuario repoUsuario, RepoProducto repoProducto) {
        super(repoTipodescuento);
        this.repoTipoDescuento = repoTipoDescuento;
        this.repoUsuario = repoUsuario;
        this.repoProducto = repoProducto;
    }

    public Optional<TipoDescuento> encuentraPorId(Long id) {
        return repoTipoDescuento.findById(id);
    }

    // Actualizar tipoDescuento en la base de datos
    @Transactional
    public TipoDescuento actualizarTipoDescuento(TipoDescuento tipoDescuento) {
        return repoTipoDescuento.save(tipoDescuento);
    }

    //Asignar tipoDescuento a producto
    public void asignarTipoDescuentoAProducto(Producto producto, TipoDescuento tipoDescuento) {
        Producto productoExistente = repoProducto.findById(producto.getId()).orElse(null);
        TipoDescuento tipoDescuentoExistente = repoTipoDescuento.findById(tipoDescuento.getId()).orElse(null);
        if (productoExistente != null && tipoDescuentoExistente != null) {
            productoExistente.getTipoDescuentos().add(tipoDescuentoExistente);
            repoProducto.save(productoExistente);
        }
    }

    //Quitar tipoDescuento a Producto
    public void quitarTipoDescuentoAProducto(Producto producto, TipoDescuento tipoDescuento) {
        Producto productoExistente = repoProducto.findById(producto.getId()).orElse(null);
        TipoDescuento tipoDescuentoExistente = repoTipoDescuento.findById(tipoDescuento.getId()).orElse(null);
        if (productoExistente != null && tipoDescuentoExistente != null) {
            productoExistente.getTipoDescuentos().remove(tipoDescuentoExistente);
            repoProducto.save(productoExistente);
        }
    }
}
