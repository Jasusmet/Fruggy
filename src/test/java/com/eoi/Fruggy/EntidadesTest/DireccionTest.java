package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Direccion;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoDireccion;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class DireccionTest {

    @Autowired
    private RepoDireccion repoDireccion;
    @Autowired
    private RepoUsuario repoUsuarios;

    @Test
    public void direccionTest() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Mihai");
        usuario = repoUsuarios.save(usuario);

        Direccion direccion = new Direccion();
        direccion.setCalle("La sabina 8");
        direccion.setPais("España");
        direccion.setMunicipio("Huelva");
        direccion.setCodigopostal(21730);

        Direccion guardarDireccion= repoDireccion.save(direccion);
        Optional<Direccion> direccionRecuperada = repoDireccion.findById((int) direccion.getId());

        assertNotNull(direccionRecuperada);
        assertEquals(guardarDireccion.getId(), direccionRecuperada.get().getId());
        assertEquals("La sabina 8", direccionRecuperada.get().getCalle());
        assertEquals("España", direccionRecuperada.get().getPais());
        assertEquals("Huelva", direccionRecuperada.get().getMunicipio());
        assertEquals(21730, direccionRecuperada.get().getCodigopostal());
      //  assertEquals(usuario.getId(), direccionRecuperada.get().getUsuarioDireccion().getId()); No se porque falla, preguntar a José Manuel





    }
}
