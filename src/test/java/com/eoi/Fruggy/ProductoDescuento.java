package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.ProductoDescuento;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.repositorios.RepoProducto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoDescuentoTest {

    @Autowired
    RepoProducto repoProducto;

    @Test

    public void crearProductoDescuentoNuevo (){

        ProductoDescuento productoDescuento = new ProductoDescuento();
        productoDescuento.setDescuento(null);
        productoDescuento.setId(10L);
        productoDescuento.setProducto(null);

        // repoProducto.save(productoDescuento);

       // assertEquals(100L, productoDescuento.getId());
    }

}


