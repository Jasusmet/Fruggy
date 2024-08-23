package com.eoi.Fruggy;



import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoSupermercado;

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
class SupermercadoTest {

    @Autowired
    RepoSupermercado repoSupermercado;

    @Test
    public void crearSupermercado() {
        Supermercado supermercado = new Supermercado();
        supermercado.setCodigopostal(null);
        supermercado.setId(10L);
        supermercado.setCalle("Nueva direccion 1");
        supermercado.setPais("España");
        supermercado.setMunicipio("Sevilla");
        supermercado.setHorario(null);
        supermercado.setNombreSuper("Supermercado");
        supermercado.setUrl(null);

        repoSupermercado.save(supermercado);

        assertEquals(10L, supermercado.getId());
        assertEquals("Nueva direccion 1", supermercado.getCalle());
        assertEquals("España", supermercado.getPais());
        assertEquals("Sevilla", supermercado.getMunicipio());
        assertEquals("Supermercado", supermercado.getNombreSuper());


    }

    @Test
    public void modificarSupermercado() {
        Supermercado supermercado = new Supermercado();
        supermercado.setCodigopostal(null);
        supermercado.setId(10L);
        supermercado.setCalle("Nueva direccion 1");
        supermercado.setPais("España");
        supermercado.setMunicipio("Sevilla");
        supermercado.setHorario(null);
        supermercado.setNombreSuper("Supermercado");
        supermercado.setUrl(null);

        repoSupermercado.save(supermercado);

        supermercado.setCodigopostal(null);
        supermercado.setId(8L);
        supermercado.setCalle("Nueva direccion 20");
        supermercado.setPais("Andorra");
        supermercado.setMunicipio("Almeria");
        supermercado.setHorario(null);
        supermercado.setNombreSuper("Super");
        supermercado.setUrl(null);

        repoSupermercado.save(supermercado);

        assertEquals(8L, supermercado.getId());
        assertEquals("Nueva direccion 20", supermercado.getCalle());
        assertEquals("Andorra", supermercado.getPais());
        assertEquals("Almeria", supermercado.getMunicipio());
        assertEquals("Super", supermercado.getNombreSuper());

    }

    @Test
    public void eliminarSupermercado() {
        Supermercado supermercado = new Supermercado();
        supermercado.setCodigopostal(null);
        supermercado.setId(10L);
        supermercado.setCalle("Nueva direccion 1");
        supermercado.setPais("España");
        supermercado.setMunicipio("Sevilla");
        supermercado.setHorario(null);
        supermercado.setNombreSuper("Supermercado");
        supermercado.setUrl(null);

        repoSupermercado.save(supermercado);

        supermercado.setCodigopostal(null);
        supermercado.setId(8L);
        supermercado.setCalle("Nueva direccion 20");
        supermercado.setPais("Andorra");
        supermercado.setMunicipio("Almeria");
        supermercado.setHorario(null);
        supermercado.setNombreSuper("Super");
        supermercado.setUrl(null);

        repoSupermercado.save(supermercado);

        repoSupermercado.delete(supermercado);

        Supermercado supermercadoEliminado = repoSupermercado.findById(8).orElse(null);


    }

}
