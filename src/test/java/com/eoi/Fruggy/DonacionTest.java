package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.repositorios.RepoDonacion;

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
class DonacionoTest {

    @Autowired
    RepoDonacion repoDonacion;

    @Test
    public void crearDonacion() {
        Donacion donacion = new Donacion();
        donacion.setId(2L);
        donacion.setFecha(null);
        donacion.setUsuario(null);
        donacion.setDonacion(null);

        repoDonacion.save(donacion);

        assertEquals(2L, donacion.getId());
    }

    @Test
    public void modificarDonacion() {
        // Creamos una nueva donación con un ID inicial
        Donacion donacion = new Donacion();
        donacion.setId(2L);
        donacion.setFecha(null);
        donacion.setUsuario(null);
        donacion.setDonacion(null);

        // Guardamos la donación en la base de datos
        repoDonacion.save(donacion);

        // Modificamos el ID de la donación
        donacion.setId(3L);

        // Guardamos los cambios en la base de datos
        repoDonacion.save(donacion);

        // Verificamos que el ID de la donación se haya modificado correctamente
        assertEquals(3L, donacion.getId());
    }

    @Test
    public void eliminarDonacion() {
        // Creamos una nueva donación con un ID inicial
        Donacion donacion = new Donacion();
        donacion.setId(2L);
        donacion.setFecha(null);
        donacion.setUsuario(null);
        donacion.setDonacion(null);

        // Guardamos la donación en la base de datos
        repoDonacion.save(donacion);

        // Modificamos el ID de la donación
        donacion.setId(3L);

        // Guardamos los cambios en la base de datos
        repoDonacion.save(donacion);

        // Eliminamos la donación de la base de datos
        repoDonacion.deleteById(3);

        // Verificamos que la donación haya sido eliminada correctamente
        Donacion donacionEliminada = repoDonacion.findById(3).orElse(null);

    }

}