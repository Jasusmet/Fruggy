package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.repositorios.RepoPrecio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class PrecioTest {

    @Autowired
    RepoPrecio repoPrecio;

    @Test
    public void nuevoPrecio() {
        Precio precio = new Precio();
        precio.setId(999L);
        precio.setActivo(true);
        precio.setValor(null);
        precio.setFechaInicio(null);
        precio.setFechaFin(null);
        precio.setSupermercado(null);
        precio.setProducto(null);

        repoPrecio.save(precio);

        assertEquals(999L, precio.getId());
        assertEquals(true, precio.getActivo());

    }
    @Test
    public void modificarPrecio() {
        Precio precio = new Precio();
        precio.setId(999L);
        precio.setActivo(true);
        precio.setValor(null);
        precio.setFechaInicio(null);
        precio.setFechaFin(null);
        precio.setSupermercado(null);
        precio.setProducto(null);

        repoPrecio.save(precio);

        precio.setId(998L);
        precio.setActivo(true);
        precio.setValor(null);
        precio.setFechaInicio(null);
        precio.setFechaFin(null);
        precio.setSupermercado(null);
        precio.setProducto(null);

        repoPrecio.save(precio);

        assertEquals(998L, precio.getId());

    }
    @Test
    public void eliminarPrecio() {
        Precio precio = new Precio();
        precio.setId(999L);
        precio.setActivo(true);
        precio.setValor(null);
        precio.setFechaInicio(null);
        precio.setFechaFin(null);
        precio.setSupermercado(null);
        precio.setProducto(null);

        repoPrecio.save(precio);

        precio.setId(998L);
        precio.setActivo(true);
        precio.setValor(null);
        precio.setFechaInicio(null);
        precio.setFechaFin(null);
        precio.setSupermercado(null);
        precio.setProducto(null);

        repoPrecio.save(precio);

        repoPrecio.delete(precio);

        Precio precioEliminado = repoPrecio.findById(998L).orElse(null);

    }

}

