package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoProducto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoTest {
    @Autowired
    private RepoProducto repoProducto;
    @Test
    public void ProductoTest() {
        Producto producto = new Producto();
        producto.setNombreProducto("Leche");
        producto.setMarca("Puleva");
        producto.setActivo(true);
        producto.setDescripcion("Es un producto buenisimo");

        Subcategoria subcategoria = new Subcategoria();
        producto.setProductoSubcategorias(subcategoria);

        Precio precio = new Precio();
        producto.setProductoPrecios(precio);

        Producto guardarProducto = repoProducto.save(producto);
        Optional<Producto> productoRecuperado = repoProducto.findById((int) guardarProducto.getId());

        assertNotNull(productoRecuperado.get().getId());
        assertEquals("Leche", productoRecuperado.get().getNombreProducto());
        assertEquals("Puleva", productoRecuperado.get().getMarca());
        assertTrue(productoRecuperado.get().getActivo());
        assertEquals("Es un producto buenisimo", productoRecuperado.get().getDescripcion());


        assertNull(productoRecuperado.get().getProductoSubcategorias());
        assertNull(productoRecuperado.get().getProductoPrecios());





    }
}
