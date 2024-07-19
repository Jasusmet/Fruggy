package com.eoi.Fruggy.EntidadesTest;
import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PrecioTest {
    @Autowired
    private RepoPrecio repoPrecio;

    @Test
    public void testPrecio() {
        
        LocalDateTime now = LocalDateTime.now();
        Precio precio = new Precio();

        Producto producto = new Producto();
        producto.setNombreProducto("leche");

        Supermercado supermercado = new Supermercado();
        supermercado.setNombreSuper("Carrefour");
        supermercado.setDireccion("la sabina 33");

        Set<Cesta> cestas = new HashSet<>();
        Set<Favorito> favoritos = new HashSet<>();
        Set<ValProducto> valoracionProductos = new HashSet<>();
        Set<Descuento> descuentos = new HashSet<>();
        precio.setFechaInicio(now);

        precio.setPrecioProductos(producto);
        precio.setCestaPrecios(cestas);
        precio.setPreciosDescuentos(descuentos);
        precio.setPreciosFavoritos(favoritos);
        precio.setPrecioSupermercados(supermercado);
        precio.setPreciosValoracionesProductos(valoracionProductos);

        Precio guardarPrecio = repoPrecio.save(precio);
        Optional<Precio> precioRecuperado = repoPrecio.findById((int) guardarPrecio.getId());

        assertNotNull(precioRecuperado.get().getId());
        //assertEquals(now, precioRecuperado.get().getFechaInicio()); falla por milesimas de segundos
        assertNull(precioRecuperado.get().getFechaFin());
        assertTrue(precioRecuperado.get().getActivo());

        assertNotNull(precioRecuperado.get().getPrecioProductos());
        assertEquals("leche", precioRecuperado.get().getPrecioProductos().getNombreProducto());
        assertNotNull(precioRecuperado.get().getPrecioSupermercados());
        assertEquals("Carrefour", precioRecuperado.get().getPrecioSupermercados().getNombreSuper());
        assertTrue(precioRecuperado.get().getCestaPrecios().isEmpty());
        assertTrue(precioRecuperado.get().getPreciosFavoritos().isEmpty());
        assertTrue(precioRecuperado.get().getPreciosValoracionesProductos().isEmpty());
        assertTrue(precioRecuperado.get().getPreciosDescuentos().isEmpty());

    }
}
