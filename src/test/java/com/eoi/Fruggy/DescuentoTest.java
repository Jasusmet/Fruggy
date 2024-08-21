package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.repositorios.RepoCategoria;
import com.eoi.Fruggy.repositorios.RepoDescuento;

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
class DescuentoTest {

    @Autowired
    RepoDescuento repoDescuento;

    @Test
    public void grabarNuevoDescuentoEnTablaDescuento() {
        // Creamos un nuevo descuento

        // Grabar en la tabla

        // Buscamos el descuento grabado en la tabla


        // Comprobamos que los detalles del descuento grabado coincidan con lo que hemos creado


    }



}
