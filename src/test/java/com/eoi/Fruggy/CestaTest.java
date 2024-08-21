package com.eoi.Fruggy;


import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CestaTest {

    @Autowired
    RepoCesta repoCesta;
    @Autowired
    RepoUsuario repoUsuario;

    @Test
    public void crearCestaNuevaYBuscarPorNombre() {
        //grabar nueva cesta
        Cesta cesta = new Cesta();
        cesta.setNombre("Nueva Cesta");
        cesta.setFecha(LocalDateTime.now());
        cesta.setUsuario(null);
        cesta.setPrecio(null);

        // persistimos la nueva cesta en la BBDD
        repoCesta.save(cesta);
        // buscar el registro recién creado
        Optional<Cesta> cesta2 = repoCesta.findByNombre("Nueva cesta");
        assertEquals("Nueva Cesta", cesta2.get().getNombre());
    }

    @Test
    public void modificarCesta() {
        // buscamos la cesta a modificar
        Optional<Cesta> cesta = repoCesta.findByNombre("Nueva cesta");
        // ahora buscamos el usuario 1 que lo vamos a asignar a la cesta
        Usuario usu = repoUsuario.findById(1L).get();
        // asignamos el usuario a la cesta
        cesta.get().setUsuario(usu);
        // grabamos la cesta en la tabla
        repoCesta.save(cesta.get());
        // volvemos a cargar la cesta para comprobar si se ha modificado el usuario
        Optional<Cesta> cesta2 = repoCesta.findByNombre("Nueva cesta");
        // comprobamos que el ide de su usuario es el id de usu
        assertEquals(usu.getId(), cesta2.get().getUsuario().getId());

    }
    @Test
    public void eliminarCesta() {
        // buscamos la cesta a eliminar
        Optional<Cesta> cesta = repoCesta.findByNombre("Nueva cesta");
        // verificamos si la cesta está presente antes de eliminarla
        if (cesta.isPresent()) {
        // eliminamos la cesta
         repoCesta.delete(cesta.get());
        } else {
        System.out.println("La cesta no fue encontrada y no se puede eliminar.");
        }
        // buscamos de nuevo la cesta para confirmar si ha sido eliminada
        Optional<Cesta> cesta2 = repoCesta.findByNombre("Nueva cesta");
        // verificamos si la cesta ya no existe
        if (cesta2.isEmpty()) {
            System.out.println("La cesta fue eliminada exitosamente.");
        } else {
            System.out.println("La cesta aún existe.");
        }
    }
}