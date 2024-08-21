package com.eoi.Fruggy;

import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.repositorios.RepoGenero;
import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class DetalleTest {

    @Autowired
    RepoDetalle repoDetalle;
    @Autowired
    RepoGenero repoGenero;
    @Autowired
    RepoUsuario repoUsuario;


    @Test
    public void crearDetalleNuevo() {
        // Creamos un nuevo detalle
        Detalle detalle = new Detalle();
        detalle.setNombre("Usuario");
        detalle.setCodigopostal(Integer.valueOf("00000"));
        detalle.setGenero(null);
        detalle.setUsuario(null);
        detalle.setCalle("Calle de prueba");
        detalle.setApellido("Apellido de prueba");
        detalle.setEdad(30);
        detalle.setMunicipio("Municipio de prueba");
        detalle.setNombreUsuario("nombreusuario");
        detalle.setId(null);

        // Guardar el detalle nuevo
        repoDetalle.save(detalle);

        // Buscar el detalle nuevo grabado
        Optional<Detalle> detalleBuscado = repoDetalle.findByNombre("Usuario");

        // Verificar que el detalle se haya guardado y encontrado correctamente
        assertEquals("Usuario", detalleBuscado.get().getNombre(), "El nombre del detalle no coincide");
    }

    }


