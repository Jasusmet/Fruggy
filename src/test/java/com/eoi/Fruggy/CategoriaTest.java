package com.eoi.Fruggy;


import com.eoi.Fruggy.entidades.*;
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
    public void grabarNuevaCategoriaEnTablaCategoria() {
        // Creamos una nueva categoria
        Categoria categoria = new Categoria("Español" , "English");
        // categoria.setTipo_es("Español");
        // categoria.setTipo_en("English");

        // Grabar en la tabla
        repoCategoria.save(categoria);

        // Buscamos la categoria grabada en la tabla
        Optional<Categoria> cat2 = repoCategoria.findByTipoes("Español");

        // Comprobamos que, por ejemplo, su nombre, es el mismo que el de la categoria que hemos creado
        assertEquals("Español", cat2.get(). getTipo_es(), "No coincide el tipo de categoria");


    }


}




