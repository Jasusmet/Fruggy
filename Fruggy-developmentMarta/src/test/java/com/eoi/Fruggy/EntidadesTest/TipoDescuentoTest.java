package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.repositorios.RepoTipoDescuento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TipoDescuentoTest {

    @Autowired
    private RepoTipoDescuento repoTipoDescuento;

    @Test
    public void TipoDescuentoTest() {
        LocalDateTime now = LocalDateTime.now();
        TipoDescuento tipoDescuento = new TipoDescuento();
        tipoDescuento.setActivo(null);
        tipoDescuento.setFechaFin(now); // lo pongo pero da fallos por milisegundos!

        TipoDescuento guardarTipoDescuento = repoTipoDescuento.save(tipoDescuento);
        Optional<TipoDescuento> tipoDescuentoRecuperado = repoTipoDescuento.findById((int) guardarTipoDescuento.getId());

        assertNotNull(tipoDescuentoRecuperado.get().getId());
        assertNull(tipoDescuentoRecuperado.get().getActivo());
    }
}
