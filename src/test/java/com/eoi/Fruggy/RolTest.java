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
    public void modificarRol() {
        // Crear un nuevo rol
        Rol rol = new Rol();
        rol.setRolNombre("invitado");
        rol.setEnDesc("friend");
        rol.setEsDesc("amigo");
        rol.setId(10L);

        // Guardar el rol
        repoRol.save(rol);

        // Verificar que el rol se guardó correctamente
        assertEquals(10L, rol.getId());
        assertEquals("invitado", rol.getRolNombre());
        assertEquals("friend", rol.getEnDesc());
        assertEquals("amigo", rol.getEsDesc());

        // Modificar el rol
        rol.setRolNombre("admin");
        rol.setEnDesc("administrator");
        rol.setEsDesc("administrador");

        // Guardar los cambios
        repoRol.save(rol);

        // Recuperar el rol modificado de la base de datos
        Rol rolModificado = repoRol.findById(rol.getId()).orElse(null);

        // Verificar que los cambios se guardaron correctamente
        assertNotNull(rolModificado);
        assertEquals(10L, rolModificado.getId());
        assertEquals("admin", rolModificado.getRolNombre());
        assertEquals("administrator", rolModificado.getEnDesc());
        assertEquals("administrador", rolModificado.getEsDesc());
    }

}