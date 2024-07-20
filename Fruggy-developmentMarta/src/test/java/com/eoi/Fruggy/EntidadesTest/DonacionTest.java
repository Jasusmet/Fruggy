package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoDonacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DonacionTest {

    @Autowired
    private RepoDonacion repoDonacion;

    @Test
    public void testDonacion() {
        Donacion donacion = new Donacion();
        donacion.setDonacion(80.00);
        donacion.setFecha(LocalDateTime.now()); // no funciona por los milisegundos

        Usuario usuario = new Usuario();
        donacion.setUsuarioDonacion(usuario);

        Donacion guardarDonacion = repoDonacion.save(donacion);
       Optional <Donacion> donacionRecuperada = repoDonacion.findById((int) guardarDonacion.getId());

        assertNotNull(donacionRecuperada.get().getId());
        assertEquals(80.00, donacionRecuperada.get().getDonacion());
        assertNotNull(donacionRecuperada.get().getFecha());
        assertNotNull(donacionRecuperada.get().getUsuarioDonacion().getId());


    }
}
