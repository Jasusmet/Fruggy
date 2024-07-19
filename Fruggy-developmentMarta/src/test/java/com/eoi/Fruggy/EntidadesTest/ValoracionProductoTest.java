package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.ValProducto;
import com.eoi.Fruggy.repositorios.RepoValProducto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ValoracionProductoTest {
    @Autowired
    private RepoValProducto repoValProduct;
    @Test
    public void valoracionProductoTest(){

        ValProducto valoracionProducto = new ValProducto();
        valoracionProducto.setNota(4.5);
        valoracionProducto.setComentario("Excelente");

        Precio precio = new Precio();
        valoracionProducto.setValoracionesProductosPrecios(precio);

        ValProducto guardarValProducto = repoValProduct.save(valoracionProducto);
        Optional<ValProducto> valProductoRecuperada= repoValProduct.findById((int) guardarValProducto.getId());


        assertNotNull(valProductoRecuperada.get().getNota());
        assertNotNull(valProductoRecuperada.get().getComentario());
        assertEquals(4.5, valProductoRecuperada.get().getNota());
        assertEquals("Excelente", valProductoRecuperada.get().getComentario());

    }
}
