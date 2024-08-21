package com.eoi.Fruggy;

import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.repositorios.RepoGenero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class GeneroTest {

    @Autowired
    RepoGenero repoGenero;


    @Test
    public void testGettersAndSetters() {
        Genero genero = new Genero();

        genero.setDescripcion("");
        assertEquals("", genero.getDescripcion());


    }
}