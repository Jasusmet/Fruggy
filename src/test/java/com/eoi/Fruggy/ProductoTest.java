package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoDescuento;

import org.junit.jupiter.api.Assertions;
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
    @Autowired
    RepoDescuento repoDescuento;

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

    @Test
    public void crearProductoConDescuento() {
        // Crear un nuevo producto
        Producto producto = new Producto();
        producto.setNombreProducto("Colonia Bebé");
        producto.setMarca("Mercadona");
        producto.setDescripcion("Colonia para bebé con un aroma suave");
        producto.setActivo(true);

        // Asignar una subcategoría al producto (suponiendo que ya existe en la base de datos)
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(1L); // Asignar un ID válido de una subcategoría existente
        producto.setSubcategoria(subcategoria);

        // Crear y asignar un descuento al producto
        Descuento descuento = new Descuento();
        descuento.setPorcentaje(Double.valueOf(10)); // 10% de descuento
        descuento.setProducto(producto);
        Set<Descuento> descuentos = new HashSet<>();
        descuentos.add(descuento);
        producto.setDescuentos(descuentos);

        // Guardar el producto
        repoProducto.save(producto);

        // Verificar que el producto se ha guardado correctamente
        assertNotNull(producto.getId());
        assertEquals("Colonia Bebé", producto.getNombreProducto());
        assertEquals("Mercadona", producto.getMarca());
        assertEquals(1, producto.getDescuentos().size());
    }



}
