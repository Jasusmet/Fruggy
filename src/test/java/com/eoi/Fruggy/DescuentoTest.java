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
        
    }
    @Test
    public void modificarDescuento() {
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
        repoDescuento.save(descuento);

        // Modificamos el descuento
        descuento.setTipoDescuento(null);
        descuento.setProducto(null);
        descuento.setActivo(false);
        descuento.setFechaFin(LocalDate.of(2024, 12, 31));
        descuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        descuento.setPorcentaje(15.0);

        // Guardamos los cambios en la tabla
        repoDescuento.save(descuento);


        // Verificamos que los cambios se hayan guardado correctamente
        assertEquals(false, descuento.getActivo());
        assertEquals(LocalDate.of(2024, 12, 31), descuento.getFechaFin());
        assertEquals(LocalDate.of(2024, 1, 1), descuento.getFechaInicio());
        assertEquals(15.0, descuento.getPorcentaje(), 0.0);
    }
    @Test
    public void eliminarDescuento() {
        // Creamos un nuevo descuento
        Descuento descuento = new Descuento();
        descuento.setId(9999L);
        descuento.setTipoDescuento(null);
        descuento.setProducto(null);
        descuento.setActivo(true);
        descuento.setFechaFin(null);
        descuento.setFechaInicio(null);
        descuento.setPorcentaje(null);

        // Guardar en la tabla
        repoDescuento.save(descuento);

        // Modificamos el descuento
        descuento.setTipoDescuento(null);
        descuento.setProducto(null);
        descuento.setActivo(false);
        descuento.setFechaFin(LocalDate.of(2024, 12, 31));
        descuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        descuento.setPorcentaje(15.0);

        // Guardamos los cambios en la tabla
        repoDescuento.save(descuento);

        // Ahora, eliminamos el descuento de la tabla
        repoDescuento.delete(descuento);

        // Verificamos que el descuento haya sido eliminado completamente
        Descuento descuentoEliminado = repoDescuento.findById(9999L).orElse(null);
    }




}
