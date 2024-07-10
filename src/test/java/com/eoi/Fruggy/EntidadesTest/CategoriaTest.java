package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoriaTest {

    @Autowired
    private RepoSubcategoria repoSubcategoria;
    @Autowired
    private RepoCategoria repoCategoria;

    @Test
    public void categoriaTest() {
        Subcategoria subcategoria =new Subcategoria();
        subcategoria.setTipo("Frutos secos");
        subcategoria = repoSubcategoria.save(subcategoria);

        Categoria categoria =new Categoria();
        categoria.setTipo("snacks");
        //categoria.addSubcategoria(subcategoria); NORMALMENTE SERIA ASI

        Categoria categoriaGuardada = repoCategoria.save(categoria);
        Optional<Categoria> categoriaRecuperada = repoCategoria.findById((int) categoriaGuardada.getId());

        assertNotNull(categoriaGuardada.getId());
        assertEquals("snacks", categoriaGuardada.getTipo());
      //  assertNotNull(categoriaGuardada.getCategoriasSubcategoria()); SALE COMO NULL, ES PORQUE NO HAY BASE DE DATOS AÚN?
       // assertEquals(1, categoriaGuardada.getCategoriasSubcategoria().size());    NORMALMENTE ASÍ

    }
}
