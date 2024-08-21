package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.ProductoEnCesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoProductoEnCesta extends JpaRepository<ProductoEnCesta, Long> {

    void deleteByCestaIdAndProductoId(Long cestaId, Long productoId);

}
