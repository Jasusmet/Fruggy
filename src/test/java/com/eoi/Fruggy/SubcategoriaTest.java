package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.ProductoDescuento;
import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubcategoriaTest {

    @Autowired
    RepoSubcategoria repoSubcategoria;

    @Test
    public void crearSubcategoria() {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(100L);
        subcategoria.setCategoria(null);
        subcategoria.setTipo(null);

        repoSubcategoria.save(subcategoria);

        assertEquals(100L, subcategoria.getId());


    }

    @Test
    public void modificarSubcategoria() {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(100L);
        subcategoria.setCategoria(null);
        subcategoria.setTipo(null);

        repoSubcategoria.save(subcategoria);

        subcategoria.setId(99L);
        subcategoria.setCategoria(null);
        subcategoria.setTipo(null);

        repoSubcategoria.save(subcategoria);
        assertEquals(99L, subcategoria.getId());
    }


    @Test
    public void eliminarSubcategoria() {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setId(100L);
        subcategoria.setCategoria(null);
        subcategoria.setTipo(null);

        repoSubcategoria.save(subcategoria);

        subcategoria.setId(99L);
        subcategoria.setCategoria(null);
        subcategoria.setTipo(null);

        repoSubcategoria.save(subcategoria);
        assertEquals(99L, subcategoria.getId());

        repoSubcategoria.delete(subcategoria);

        Subcategoria subcategoriaEliminada = repoSubcategoria.findById(99L).get();

        assertNull(subcategoriaEliminada);
    }
    }

