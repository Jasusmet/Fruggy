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

        // Creamos un nuevo genero
        Genero genero = new Genero();
        genero.setId(1L);


        // Creamos un nuevo detalle
        Detalle detalle = new Detalle();
        detalle.setNombre("Ana");
        detalle.setApellido("García");
        detalle.setEdad(25);
        detalle.setCalle("Calle Falsa 123");
        detalle.setMunicipio("Municipio Ficticio");
        detalle.setPais("País Imaginario");
        detalle.setCodigopostal(54321);
        detalle.setGenero(genero);

        // Guardar el nuevo detalle
        repoGenero.save(genero);
        repoDetalle.save(detalle);

        // Verificar que el detalle se haya guardado correctamente
        assertNotNull(detalle.getId(), "El ID del detalle guardado no debe ser null");
        assertEquals("Ana", detalle.getNombre(), "El nombre del detalle guardado no coincide");
        assertEquals("García", detalle.getApellido(), "El apellido del detalle guardado no coincide");
        assertEquals(25, detalle.getEdad(), "La edad del detalle guardado no coincide");
        assertEquals("Calle Falsa 123", detalle.getCalle(), "La calle del detalle guardado no coincide");
        assertEquals("Municipio Ficticio", detalle.getMunicipio(), "El municipio del detalle guardado no coincide");
        assertEquals("País Imaginario", detalle.getPais(), "El país del detalle guardado no coincide");
        assertEquals(54321, detalle.getCodigopostal(), "El código postal del detalle guardado no coincide");
    }

    @Test
    public void modificarDetalle() {
        // Primero, crear un detalle nuevo para asegurarse de que haya uno para modificar
        crearDetalleNuevo();

        // Buscar el detalle guardado
        Detalle detalleExistente = repoDetalle.findByNombre("Ana").orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        // Modificar todos los campos del detalle
        detalleExistente.setNombre("Carlos");
        detalleExistente.setApellido("Martínez");
        detalleExistente.setEdad(40);
        detalleExistente.setCalle("Avenida Prueba 456");
        detalleExistente.setMunicipio("Municipio Real");
        detalleExistente.setPais("País Verdadero");
        detalleExistente.setCodigopostal(98765);

        // Guardar los cambios
        Detalle detalleModificado = repoDetalle.save(detalleExistente);

        // Verificar que el detalle se haya modificado correctamente
        assertNotNull(detalleModificado.getId(), "El ID del detalle modificado no debe ser null");
        assertEquals("Carlos", detalleModificado.getNombre(), "El nombre del detalle modificado no coincide");
        assertEquals("Martínez", detalleModificado.getApellido(), "El apellido del detalle modificado no coincide");
        assertEquals(40, detalleModificado.getEdad(), "La edad del detalle modificado no coincide");
        assertEquals("Avenida Prueba 456", detalleModificado.getCalle(), "La calle del detalle modificado no coincide");
        assertEquals("Municipio Real", detalleModificado.getMunicipio(), "El municipio del detalle modificado no coincide");
        assertEquals("País Verdadero", detalleModificado.getPais(), "El país del detalle modificado no coincide");
        assertEquals(98765, detalleModificado.getCodigopostal(), "El código postal del detalle modificado no coincide");
    }

    @Test
    public void eliminarDetalle() {
        // Crear un detalle nuevo para asegurarse de que haya uno para eliminar
        crearDetalleNuevo();

        // Buscar el detalle guardado
        Detalle detalleExistente = repoDetalle.findByNombre("Ana").orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        // Eliminar el detalle
        repoDetalle.delete(detalleExistente);

        // Verificar que el detalle ha sido eliminado
        Optional<Detalle> detalleEliminado = repoDetalle.findById(detalleExistente.getId());

    }

    }


