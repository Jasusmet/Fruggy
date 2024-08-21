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
    @Test
    public void modificarCategoria() {
        // buscamos la categoria a modificar
        Optional<Categoria> cat2 = repoCategoria.findByTipoes("Español");

        // cambiamos el nombre Español a Fantasma
        Categoria categoria = cat2.get();
        categoria.setTipo_es("Fantasma");

        // grabamos la categoria en la tabla
        repoCategoria.save(categoria);

        // volvemos a cargar la tabla para comprobar si se ha modificado
        Optional<Categoria> catModificada = repoCategoria.findByTipoes("Fantasma");

        // comprobamos que el nombre ha cambiado a fantasma
        assertEquals("Fantasma", catModificada.get().getTipo_es());

    }

    @Test
    public void eliminarCategoria() {
        // buscamos la categoria a eliminar
        Optional<Categoria> catModificada = repoCategoria.findByTipoes("Fantasma");

        // verificamos si la categoria está presente antes de eliminarla
        if (catModificada.isPresent()) {
            // eliminamos la categoria
            repoCategoria.delete(catModificada.get());
        } else {
            System.out.println("La categoria no fue encontrada y no se puede eliminar.");
        }

        // buscamos de nuevo la categoria para confirmar si ha sido eliminada
        Optional<Categoria> catEliminada = repoCategoria.findByTipoes("Fantasma");

        // verificamos si la categoria ya no existe
        if (catEliminada.isEmpty()) {
            System.out.println("La categoria fue eliminada exitosamente.");
        } else {
            System.out.println("La categoria aún existe.");
        }

    }

}




