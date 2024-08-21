package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Descuento;
import com.eoi.Fruggy.entidades.Detalle;
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
        Descuento descuento = new Descuento();
        descuento.setId(9999L);
        descuento.setTipoDescuento(null);
        descuento.setProducto(null);
        descuento.setActivo(true);
        descuento.setFechaFin(null);
        descuento.setFechaInicio(null);
        descuento.setPorcentaje(null);

        // Grabar en la tabla
        repoDescuento.save(descuento) ;

        // Buscamos el descuento grabado en la tabla

        assertEquals(9999L, descuento.getId().longValue());
        assertEquals(true, descuento.getActivo());



        // Comprobamos que los detalles del descuento grabado coincidan con lo que hemos creado


    }



}
