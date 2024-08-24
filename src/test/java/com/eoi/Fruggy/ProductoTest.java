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
        // Creamos un nuevo producto
        Producto producto = new Producto();
        producto.setNombreProducto("Colonia Bebé");
        producto.setMarca("Mercadona");
        producto.setDescripcion("Colonia para bebé con un aroma suave");
        producto.setActivo(true);

        // Asignamos una subcategoría al producto
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(1L);
        producto.setSubcategoria(subcategoria);

        // Creamos y asignamos un descuento al producto
        Descuento descuento = new Descuento();
        descuento.setPorcentaje(Double.valueOf(10));
        descuento.setProducto(producto);
        Set<Descuento> descuentos = new HashSet<>();
        descuentos.add(descuento);
        producto.setDescuentos(descuentos);

        // Guardamos el producto
        repoProducto.save(producto);

        // Verificamos que el producto se ha guardado correctamente
        assertNotNull(producto.getId());
        assertEquals("Colonia Bebé", producto.getNombreProducto());
        assertEquals("Mercadona", producto.getMarca());
        assertEquals(1, producto.getDescuentos().size());
    }

    @Test
    public void modificarProductoConDescuento() {
        // Creamos un nuevo producto
        Producto producto = new Producto();
        producto.setNombreProducto("Colonia Bebé");
        producto.setMarca("Mercadona");
        producto.setDescripcion("Colonia para bebé con un aroma suave");
        producto.setActivo(true);

        // Asignamos una subcategoría al producto
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(1L);
        producto.setSubcategoria(subcategoria);

        // Creamos y asignamos un descuento al producto
        Descuento descuento = new Descuento();
        descuento.setPorcentaje(Double.valueOf(10));
        descuento.setProducto(producto);
        Set<Descuento> descuentos = new HashSet<>();
        descuentos.add(descuento);
        producto.setDescuentos(descuentos);

        // Guardamos el producto
        repoProducto.save(producto);

        producto.getDescuentos().clear();

        Descuento nuevoDescuento = new Descuento();
        nuevoDescuento.setPorcentaje(Double.valueOf(20));
        nuevoDescuento.setProducto(producto);

        producto.getDescuentos().add(nuevoDescuento);

        repoProducto.save(producto);

        Producto productoVerificado = repoProducto.findById(producto.getId()).get();

        assertEquals(1, productoVerificado.getDescuentos().size());

        Descuento descuentoVerificado = productoVerificado.getDescuentos().iterator().next();
        assertEquals(Double.valueOf(20), descuentoVerificado.getPorcentaje());
    }


}
