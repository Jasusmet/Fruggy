package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Descuento;
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
class ProductoTest {

    @Autowired
    RepoProducto repoProducto;

    @Test

    public void nuevoProducto() {
        Producto producto = new Producto();
        producto.setId(999L);
        producto.setActivo(true);
        producto.setDescripcion(null);
        producto.setSubcategoria(null);
        producto.setMarca("Mercadona");
        producto.setNombreProducto("Colonia bebe");

        repoProducto.save(producto);

        assertEquals(999L, producto.getId().longValue());
        assertEquals("Mercadona", producto.getMarca()); // Corrige el campo a verificar
        assertEquals("Colonia bebe", producto.getNombreProducto());

    }

    @Test
    public void modificarProducto() {
        Producto producto = new Producto();
        producto.setId(999L);
        producto.setActivo(true);
        producto.setDescripcion(null);
        producto.setSubcategoria(null);
        producto.setMarca("Mercadona");
        producto.setNombreProducto("Colonia bebe");

        repoProducto.save(producto);

        producto.setId(998L);
        producto.setActivo(false);
        producto.setDescripcion(null);
        producto.setSubcategoria(null);
        producto.setMarca("Dia");
        producto.setNombreProducto("Colonia bebe");

        repoProducto.save(producto);

        assertEquals(998L, producto.getId().longValue());
        assertEquals("Dia", producto.getMarca());
        assertEquals("Colonia bebe", producto.getNombreProducto());
    }

    @Test
    public void eliminarProducto() {
        Producto producto = new Producto();
        producto.setId(999L);
        producto.setActivo(true);
        producto.setDescripcion(null);
        producto.setSubcategoria(null);
        producto.setMarca("Mercadona");
        producto.setNombreProducto("Colonia bebe");

        repoProducto.save(producto);

        producto.setId(998L);
        producto.setActivo(false);
        producto.setDescripcion(null);
        producto.setSubcategoria(null);
        producto.setMarca("Dia");
        producto.setNombreProducto("Colonia bebe");

        repoProducto.save(producto);
        repoProducto.delete(producto);

        Producto productoEliminado = repoProducto.findById(998L).orElse(null);

        assertNull(productoEliminado);


    }
}
