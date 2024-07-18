package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.repositorios.RepoGenero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GeneroTest {

    @Autowired
    private RepoGenero repoGenero;
    @Test
    public void test() {

        Genero genero = new Genero();
        genero.setDescripcion("Masculino");

        Set<Detalle> detallesGenero = new HashSet<>();
        Detalle detalle1 = new Detalle();
        Detalle detalle2 = new Detalle();
        detallesGenero.add(detalle1);
        detallesGenero.add(detalle2);
        genero.setDetallesGenero(detallesGenero);

        Genero guardarGenero = repoGenero.save(genero);
        Optional<Genero> generoRecuperado = repoGenero.findById((int) guardarGenero.getId());

        assertNotNull(generoRecuperado.get().getId());
        assertEquals("Masculino", generoRecuperado.get().getDescripcion());
        assertNotNull(generoRecuperado.get().getDetallesGenero());
        assertEquals(0, generoRecuperado.get().getDetallesGenero().size()); // Pasa lo mismo que con genero, debería haber 2
    }
}
