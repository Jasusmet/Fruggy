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
    public void crearRol() {
    Rol rol = new Rol();
    rol.setRolNombre("invitado");
    rol.setEnDesc("friend");
    rol.setEsDesc("amigo");
    rol.setId(10L);

    repoRol.save(rol);

    assertEquals(10L, rol.getId());
    assertEquals("invitado", rol.getRolNombre());
    assertEquals("friend", rol.getEnDesc());
    assertEquals("amigo", rol.getEsDesc());

    }

 // Falla modificar

    @Test
    public void modificarRol() {
        Rol rol = new Rol();
        rol.setRolNombre("invitado");
        rol.setEnDesc("friend");
        rol.setEsDesc("amigo");
        rol.setId(10L);

        repoRol.save(rol);

        rol.setRolNombre("invitado");
        rol.setEnDesc("friend");
        rol.setEsDesc("amigo");
        rol.setId(11L);

        repoRol.save(rol);

        assertEquals(11L, rol.getId());
    }
}