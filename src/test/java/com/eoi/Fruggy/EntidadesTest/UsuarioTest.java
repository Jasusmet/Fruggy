package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioTest {

    @Autowired
    private RepoUsuario repoUsuarios;

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("TestUser");
        usuario.setPassword("TestPassword");
        usuario.setTelefono("1234567890");
        repoUsuarios.save(usuario);

        Optional<Usuario> usuarioRecuperado = repoUsuarios.findById((int) usuario.getId());
        assertTrue(usuarioRecuperado.isPresent());
        assertEquals("TestUser", usuarioRecuperado.get().getNombreUsuario());
        assertEquals("TestPassword", usuarioRecuperado.get().getPassword());
        assertEquals("1234567890", usuarioRecuperado.get().getTelefono());
    }
}
