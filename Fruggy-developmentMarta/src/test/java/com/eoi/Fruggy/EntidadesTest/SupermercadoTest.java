package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoSupermercado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SupermercadoTest {
    @Autowired
    private RepoSupermercado repoSupermercado;

    @Test
    public void supermercadoGuardarTest() {
        Supermercado supermercado = new Supermercado();
        supermercado.setNombreSuper("Lidl");
        supermercado.setUrl("http://lidl.com");
        supermercado.setImagenPath("/images/lidl.png");
        supermercado.setHorario("8:00 - 22:00");
        supermercado.setDireccion("Calle La Sabina 99");

        // Guardar el supermercado
        Supermercado supermercadoGuardado = repoSupermercado.save(supermercado);

        // Recuperar el supermercado por ID
        Optional<Supermercado> supermercadoRecuperado = repoSupermercado.findById((int) supermercadoGuardado.getId());

        // Verificar que el supermercado se recupera correctamente
        assertTrue(supermercadoRecuperado.isPresent());
        assertEquals("Lidl", supermercadoRecuperado.get().getNombreSuper());
        assertEquals("http://lidl.com", supermercadoRecuperado.get().getUrl());
        assertEquals("/images/lidl.png", supermercadoRecuperado.get().getImagenPath());
        assertEquals("8:00 - 22:00", supermercadoRecuperado.get().getHorario());
        assertEquals("Calle La Sabina 99", supermercadoRecuperado.get().getDireccion());
    }
}

