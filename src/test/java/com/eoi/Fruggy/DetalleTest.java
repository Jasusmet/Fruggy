package com.eoi.Fruggy;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class DetalleTest {

    @Autowired
    RepoDetalle repoDetalle;

    @Test
    public void crearDetalleNuevo() {
        Detalle detalle = new Detalle();
        detalle.setNombre("Detalle nuevo");
        detalle.setCodigopostal(null);
        detalle.setGenero(null);
        detalle.setUsuario(null);
        detalle.setCalle(null);
        detalle.setApellido(null);
        detalle.setEdad(null);
        detalle.setMunicipio(null);
        detalle.setNombreUsuario(null);
        detalle.setId(null);

        repoDetalle.save(detalle);
        assertNotNull(detalle.getId());

        assertEquals("Detalle nuevo", detalle.getNombre());
    }
    }


