package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SrvcUsuarioTests {
    @Autowired
    private SrvcUsuario srvcUsuario;

    @Autowired
    private RepoUsuario repoUsuario;

    @Test
    public void testGuardarUsuarioConRoles() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("password");
        usuario.setActive(true);

        Rol rol = new Rol();
        rol.setRolNombre("USER");
        usuario.setRoles(Set.of(rol));

        srvcUsuario.guardar(usuario);

        // Verificar que el usuario se guarda correctamente
        Usuario usuarioGuardado = repoUsuario.findById((int) usuario.getId()).orElse(null);
        assertNotNull(usuarioGuardado);
        assertTrue(usuarioGuardado.getRoles().stream().anyMatch(r -> "USER".equals(r.getRolNombre())));
    }
}

