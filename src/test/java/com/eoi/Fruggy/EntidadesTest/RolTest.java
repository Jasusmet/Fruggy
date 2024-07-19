package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoRol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RolTest {
    @Autowired
    private RepoRol repoRol;

    @Test
    public void testCreateAndSaveRol() {
        Rol rol = new Rol();
        rol.setRolNombre("Admin");

        Rol guardarRol = repoRol.save(rol);

        assertEquals("Admin", guardarRol.getRolNombre());

        assertNull(guardarRol.getUsuarios());

        Usuario usuario = new Usuario();
        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(usuario);
        guardarRol.setUsuarios(usuarios);

        repoRol.save(guardarRol);

        Rol rolguardado = repoRol.findById((long) guardarRol.getId()).get();

        assertEquals("Admin", rolguardado.getRolNombre());
        assertNotNull(rolguardado.getUsuarios());
    }
}