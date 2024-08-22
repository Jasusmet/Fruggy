package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.ValoracionProducto;
import com.eoi.Fruggy.entidades.ValoracionSupermercado;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoValSupermercado;

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
class ValoracionSupermercadoTest {

    @Autowired
    RepoValSupermercado repoValSupermercado;

    @Test
    public void crearValoracionSupermercado() {
        ValoracionSupermercado valoracionSupermercado = new ValoracionSupermercado();
        valoracionSupermercado.setNota(null);
        valoracionSupermercado.setId(10L);
        valoracionSupermercado.setSupermercado(null);
        valoracionSupermercado.setUsuario(null);
        valoracionSupermercado.setComentario(null);

        repoValSupermercado.save(valoracionSupermercado);

        assertEquals(10L, valoracionSupermercado.getId());

    }


    @Test
    public void modificarValoracionSupermercado() {
        ValoracionSupermercado valoracionSupermercado = new ValoracionSupermercado();
        valoracionSupermercado.setNota(null);
        valoracionSupermercado.setId(10L);
        valoracionSupermercado.setSupermercado(null);
        valoracionSupermercado.setUsuario(null);
        valoracionSupermercado.setComentario(null);

        repoValSupermercado.save(valoracionSupermercado);

        valoracionSupermercado.setNota(null);
        valoracionSupermercado.setId(9L);
        valoracionSupermercado.setSupermercado(null);
        valoracionSupermercado.setUsuario(null);
        valoracionSupermercado.setComentario(null);

        repoValSupermercado.save(valoracionSupermercado);

        assertEquals(9L, valoracionSupermercado.getId());

    }

    @Test
    public void eliminarValoracionSupermercado() {
        ValoracionSupermercado valoracionSupermercado = new ValoracionSupermercado();
        valoracionSupermercado.setNota(null);
        valoracionSupermercado.setId(10L);
        valoracionSupermercado.setSupermercado(null);
        valoracionSupermercado.setUsuario(null);
        valoracionSupermercado.setComentario(null);

        repoValSupermercado.save(valoracionSupermercado);

        valoracionSupermercado.setNota(null);
        valoracionSupermercado.setId(9L);
        valoracionSupermercado.setSupermercado(null);
        valoracionSupermercado.setUsuario(null);
        valoracionSupermercado.setComentario(null);

        repoValSupermercado.save(valoracionSupermercado);
        repoValSupermercado.delete(valoracionSupermercado);
    }
}
