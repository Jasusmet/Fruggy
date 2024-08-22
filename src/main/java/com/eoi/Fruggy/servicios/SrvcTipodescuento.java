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

    @Transactional
    public TipoDescuento encuentraPorTipo(String tipo) {
        return repoTipoDescuento.findByTipo(tipo);
    }
}
