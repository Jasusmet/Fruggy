package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.ValoracionProducto;
import com.eoi.Fruggy.repositorios.RepoValProducto;

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
class ValoracionProductoTest {

    @Autowired
    RepoValProducto repoValProducto;

    @Test
    public void crearValoracionProducto() {
        ValoracionProducto valoracionProducto = new ValoracionProducto();
        valoracionProducto.setNota(null);
        valoracionProducto.setId(10L);
        valoracionProducto.setProducto(null);
        valoracionProducto.setUsuario(null);
        valoracionProducto.setComentario(null);

        repoValProducto.save(valoracionProducto);

        assertEquals(10L, valoracionProducto.getId());

    }

    @Test
    public void modificarValoracionProducto() {
        ValoracionProducto valoracionProducto = new ValoracionProducto();
        valoracionProducto.setNota(null);
        valoracionProducto.setId(10L);
        valoracionProducto.setProducto(null);
        valoracionProducto.setUsuario(null);
        valoracionProducto.setComentario(null);

        repoValProducto.save(valoracionProducto);

        valoracionProducto.setNota(null);
        valoracionProducto.setId(9L);
        valoracionProducto.setProducto(null);
        valoracionProducto.setUsuario(null);
        valoracionProducto.setComentario(null);

        repoValProducto.save(valoracionProducto);

        assertEquals(9L, valoracionProducto.getId());
    }
    @Test
    public void eliminarValoracionProducto() {
        ValoracionProducto valoracionProducto = new ValoracionProducto();
        valoracionProducto.setNota(null);
        valoracionProducto.setId(10L);
        valoracionProducto.setProducto(null);
        valoracionProducto.setUsuario(null);
        valoracionProducto.setComentario(null);

        repoValProducto.save(valoracionProducto);

        valoracionProducto.setNota(null);
        valoracionProducto.setId(9L);
        valoracionProducto.setProducto(null);
        valoracionProducto.setUsuario(null);
        valoracionProducto.setComentario(null);

        repoValProducto.save(valoracionProducto);
        repoValProducto.delete(valoracionProducto);
    }

}