package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.repositorios.RepoImagen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class ImagenTest {

    @Autowired
    RepoImagen repoImagen;

    @Test
    public void crearImagen() {
        Imagen imagen = new Imagen();
        imagen.setRutaImagen(null);
        imagen.setId(1L);
        imagen.setDetalle(null);
        imagen.setUsuario(null);
        imagen.setNombreArchivo("nueva imagen");
        imagen.setProductos(null);
        imagen.setSupermercado(null);

        repoImagen.save(imagen);


        assertEquals("nueva imagen", imagen.getNombreArchivo());
        assertEquals(1L, imagen.getId());

    }

    @Test
    public void modificarImagen(){
        Imagen imagen = new Imagen();
        imagen.setRutaImagen(null);
        imagen.setId(1L);
        imagen.setDetalle(null);
        imagen.setUsuario(null);
        imagen.setNombreArchivo("nueva imagen");
        imagen.setProductos(null);
        imagen.setSupermercado(null);

        repoImagen.save(imagen);

        imagen.setId(2L);
        imagen.setNombreArchivo("nuevisima imagen");

        repoImagen.save(imagen);

        assertEquals("nuevisima imagen", imagen.getNombreArchivo());
        assertEquals(2L, imagen.getId());

    }

    @Test
    public void eliminarImagen() {
        Imagen imagen = new Imagen();
        imagen.setRutaImagen(null);
        imagen.setId(1L);
        imagen.setDetalle(null);
        imagen.setUsuario(null);
        imagen.setNombreArchivo("nueva imagen");
        imagen.setProductos(null);
        imagen.setSupermercado(null);

        repoImagen.save(imagen);

        imagen.setId(2L);

        repoImagen.save(imagen);

        repoImagen.deleteById(2L);

        Imagen imagenEliminada = repoImagen.findById(2L).orElse(null);

    }

}

