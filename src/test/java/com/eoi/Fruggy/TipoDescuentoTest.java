package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.repositorios.RepoTipoDescuento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TipoDescuentoTest {

    @Autowired
    RepoTipoDescuento repoTipoDescuento;

    @Test
    public void CrearTipoDescuento() {
        TipoDescuento tipoDescuento = new TipoDescuento();
        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaFin(LocalDate.of(2025, 12, 31));
        tipoDescuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        tipoDescuento.setId(10L);
        tipoDescuento.setTipo("Descuento por Volumen");

        repoTipoDescuento.save(tipoDescuento);

        assertEquals(10L, tipoDescuento.getId());
        assertEquals("Descuento por Volumen", tipoDescuento.getTipo());
        assertEquals(LocalDate.of(2025, 12, 31), tipoDescuento.getFechaFin());
        assertEquals(LocalDate.of(2024, 1, 1), tipoDescuento.getFechaInicio());
        assertEquals(true, tipoDescuento.getActivo());


    }

    @Test
    public void modificarTipoDescuento() {
        TipoDescuento tipoDescuento = new TipoDescuento();
        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaFin(LocalDate.of(2025, 12, 31));
        tipoDescuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        tipoDescuento.setId(10L);
        tipoDescuento.setTipo("Descuento por Volumen");

        repoTipoDescuento.save(tipoDescuento);

        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaFin(LocalDate.of(2025, 12, 31));
        tipoDescuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        tipoDescuento.setId(90L);
        tipoDescuento.setTipo("Descuento por Volumen");

        repoTipoDescuento.save(tipoDescuento);

        assertEquals(90L, tipoDescuento.getId());

    }

    @Test
    public void eliminarTipoDescuento() {
        TipoDescuento tipoDescuento = new TipoDescuento();
        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaFin(LocalDate.of(2025, 12, 31));
        tipoDescuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        tipoDescuento.setId(10L);
        tipoDescuento.setTipo("Descuento por Volumen");

        repoTipoDescuento.save(tipoDescuento);

        tipoDescuento.setActivo(true);
        tipoDescuento.setFechaFin(LocalDate.of(2025, 12, 31));
        tipoDescuento.setFechaInicio(LocalDate.of(2024, 1, 1));
        tipoDescuento.setId(90L);
        tipoDescuento.setTipo("Descuento por Volumen");

        repoTipoDescuento.save(tipoDescuento);
        repoTipoDescuento.delete(tipoDescuento);

        TipoDescuento tipoDescuentoEliminado = repoTipoDescuento.findById(90L).orElse(null);


    }
}
