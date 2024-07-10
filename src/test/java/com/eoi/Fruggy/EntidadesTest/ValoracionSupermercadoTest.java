package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValoracionSupermercadoTest {

    @Autowired
    private RepoValSupermercado repoValSuper;

    @Test
    public void valoracionSupermercadoGuardarTest() {
        Supermercado supermercado = new Supermercado();
        supermercado.setNombreSuper("Lidl");

        ValSupermercado valoracionSupermercado = new ValSupermercado();
        valoracionSupermercado.setComentario("Excelente supermercado!");
        valoracionSupermercado.setNota(4.5);
        valoracionSupermercado.setValoracionSupermercadoSupermercado(supermercado);

        repoValSuper.save(valoracionSupermercado);
        Optional<ValSupermercado> valoracionRecuperada = repoValSuper.findById((int) valoracionSupermercado.getId());

        assertTrue(valoracionRecuperada.isPresent());
        assertEquals("Excelente supermercado!", valoracionRecuperada.get().getComentario());
        assertEquals(4.5, valoracionRecuperada.get().getNota());
        assertEquals(supermercado, valoracionRecuperada.get().getValoracionSupermercadoSupermercado());
    }

    }
