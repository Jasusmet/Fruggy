package com.eoi.Fruggy;


import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoCategoria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class CategoriaTest {

    @Autowired
    RepoCategoria repoCategoria;

    @Test
    public void testCategoriaCreation() {
        Categoria categoria = new Categoria();
        assertNotNull(categoria);
    }

    @Test
    public void testGettersAndSetters() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTipo_es("");
        categoria.setTipo_en("");

        Set<Subcategoria> subcategorias = new HashSet<>();
        categoria.setSubcategorias(subcategorias);

        assertEquals(1L, categoria.getId());
        assertEquals("", categoria.getTipo_es());
        assertEquals("", categoria.getTipo_en());
        assertEquals(subcategorias, categoria.getSubcategorias());


    }
}




