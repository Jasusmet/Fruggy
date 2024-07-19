package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DetalleTest {

    @Autowired
    private RepoDetalle repoDetalle;
//    @Autowired
//    private RepoGenero repoGenero;
//    @Autowired
//    private RepoUsuarios repoUsuarios;
    // Debería funcionar con estos repositorios, pero da error...

    @Test
    public void test() {
      //  Genero genero = new Genero();
        //  genero.setDetallesGenero("Masculino"); ERROR
      //  genero = repoGenero.save(genero); ES PORQUE ESTA EN DROP-CREATE?



        Detalle detalle = new Detalle();
        detalle.setNombreUsuario("nombreusuario1");
        detalle.setNombre("Mihai");
        detalle.setApellido("Livadaru");
        detalle.setPathImagen("/images/mihai.jpg");
        detalle.setEdad(27);
        //detalle.setDetalleGenero(genero);

        Detalle detalleGuardado = repoDetalle.save(detalle);
        Optional <Detalle> detalleRecuperado= repoDetalle.findById((int) detalleGuardado.getId());


        assertNotNull(detalleRecuperado.get().getId());
        assertEquals("nombreusuario1", detalleRecuperado.get().getNombreUsuario());
        assertEquals("Mihai", detalleRecuperado.get().getNombre());
        assertEquals("Livadaru", detalleRecuperado.get().getApellido());
        assertEquals("/images/mihai.jpg", detalleRecuperado.get().getPathImagen());
        assertEquals(27, detalleRecuperado.get().getEdad());
      //  assertEquals(genero.getId(), detalleRecuperado.get().getDetalleGenero().getId());

    }
}
