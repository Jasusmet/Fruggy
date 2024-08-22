package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import com.eoi.Fruggy.repositorios.RepoDetalle;

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
class UsuarioTest {

    @Autowired
    RepoUsuario repoUsuario;
    RepoDetalle repoDetalle;

    @Test
    public void crearUsuario() {

        Usuario usuario = new Usuario();
        usuario.setActive(null);
        usuario.setId(100L);
        usuario.setTelefono(null);
        usuario.setEmail("email@email.com");
        usuario.setPassword("password");

        repoUsuario.save(usuario);

        assertEquals(100L, usuario.getId());
        assertEquals("email@email.com", usuario.getEmail());
        assertEquals("Nombre", usuario.getDetalle().getNombre());
        assertEquals(true,usuario.getActive());

        }


    }




