package com.eoi.Fruggy;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoGenero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GeneroTest {

    @Autowired
    RepoGenero repoGenero;


    @Test
    public void crearNuevoGenero() {
        // creamos un nuevo genero
        Genero genero = new Genero();
        genero.setDescripcion("Alien");

        // guardamos el nuevo genero
        repoGenero.save(genero);

        // buscamos que ha sido creado
        Optional<Genero> generoOptional = repoGenero.findById(genero.getId());
        assertEquals(genero.getDescripcion(), "Alien");

    }

    @Test
    public void modificarGenero() {

        Genero genero = new Genero();
        genero.setDescripcion("Alien");

        repoGenero.save(genero);

        Optional<Genero> generoOptional = repoGenero.findById(genero.getId());
        assertTrue(generoOptional.isPresent());
        assertEquals("Alien", generoOptional.get().getDescripcion());

        genero.setDescripcion("Fantasma");

        repoGenero.save(genero);

        Genero generoModificado = repoGenero.findById(genero.getId()).orElse(null);
        assertNotNull(generoModificado);
        assertEquals("Fantasma", generoModificado.getDescripcion());
    }
    @Test
    public void eliminarGenero() {

        Genero genero = new Genero();
        genero.setDescripcion("Alien");

        repoGenero.save(genero);

        Optional<Genero> generoOptional = repoGenero.findById(genero.getId());
        assertTrue(generoOptional.isPresent());
        assertEquals("Alien", generoOptional.get().getDescripcion());

        repoGenero.delete(genero);

        Optional<Genero> generoEliminado = repoGenero.findById(genero.getId());
        assertFalse(generoEliminado.isPresent());
    }

}
