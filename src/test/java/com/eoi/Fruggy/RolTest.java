package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoRol;

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
class RolTest {

    @Autowired
    RepoRol repoRol;

    @Test
    public void crearNuevoRol(){
        // Crear un nuevo rol
        Rol rol = new Rol();
        rol.setRolNombre("invitado");
        rol.setEnDesc("friend");
        rol.setEsDesc("amigo");
        rol.setId(10L);

        // Guardar el rol
        repoRol.save(rol);

        // Buscar y leer el rol creado para compararlo con el original
        Rol rol2 =  repoRol.findByRolNombre("invitado").get();

        // Verificar que el rol leido es igual al que se guardó correctamente
        //assertEquals(10L, rol.getId());
        assertEquals(rol.getRolNombre(), rol2.getRolNombre());
        assertEquals(rol.getEnDesc(), rol2.getEnDesc());
        assertEquals(rol.getEsDesc(), rol2.getEsDesc());
    }

    @Test
    public void modificarRol() {
        // leemos el rol a modificar
        Rol rol = repoRol.findByRolNombre("invitado").get();

        // Modificamos el rol
        rol.setEsDesc("amigo modificado");

        // Guardar los cambios
        repoRol.save(rol);

        // leemos de nuevo el rol para comprobar si se ha guardado la modificación
        Rol rol2 =  repoRol.findByRolNombre("invitado").get();

        // comparamos
        assertEquals("amigo modificado", rol2.getEsDesc());
    }

    @Test
    public void eliminarRol() {
        // buscamos el rol a eliminar
        Rol rol = repoRol.findByRolNombre("invitado").get();

        // lo eliminamos de la tabla
        repoRol.delete(rol);

        // Volvemos a buscar. Esta vez, el repositorio devuelve un optional con un valor null dentro
        // hacemos la comprobación --> ESCRIBELA TU
    }
}