package com.eoi.Fruggy.repositorios;

import com.eoi.Fruggy.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoProducto extends JpaRepository<Producto, Long> {
    Optional<Producto> findProductoByNombreProductoAndMarca(String nombreProducto, String marca);
    //List<Producto> findByDescuentoActivoTrue();
}
