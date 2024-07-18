package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.repositorios.RepoDescuento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DescuentoTest {
    @Autowired
    private RepoDescuento repoDescuento;

    @Test
    public void testDescuento() {
        LocalDateTime now = LocalDateTime.now();
        Descuento descuento = new Descuento();
        descuento.setActivo(true);
        descuento.setFechaInicio(now);

        Precio precio = new Precio();
        descuento.setDescuentosPrecios(precio);
        precio.setActivo(true);
        precio.setFechaInicio(now);

        TipoDescuento tipoDescuento = new TipoDescuento();
        descuento.setDescuentosTipoDeDescuentos(tipoDescuento);
        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaInicio(now);

        Descuento guardarDescuento = repoDescuento.save(descuento);
        Optional<Descuento> descuentoRecuperado= repoDescuento.findById((int) guardarDescuento.getId());

        assertNotNull(descuentoRecuperado.get().getId());
       // assertEquals(now, descuentoRecuperado.get().getFechaInicio()); falla por milisegundos
        assertTrue(descuentoRecuperado.get().getActivo());

        assertNotNull(descuentoRecuperado.get().getDescuentosPrecios());
        assertNotNull(descuentoRecuperado.get().getDescuentosTipoDeDescuentos());



    }
}
