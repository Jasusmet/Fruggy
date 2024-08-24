package com.eoi.Fruggy;




import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import com.eoi.Fruggy.repositorios.RepoDetalle;
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
class UsuarioTest {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoRol repoRol;

    @Test
    public void crearNuevoUsuario() {

        // Creamos un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("johndoe@example.com");
        usuario.setPassword("Password@1");
        usuario.setTelefono("1234567890");
        usuario.setActive(true);

        // Creamos y asignamos detalle
        Detalle detalle = new Detalle();
        detalle.setNombre("John");
        usuario.setDetalle(detalle);

        // Guardamos el usuario
        usuario = repoUsuario.save(usuario);

        // Verificamos los valores
        assertEquals("johndoe@example.com", usuario.getEmail());
        assertEquals("1234567890", usuario.getTelefono());
        assertEquals("Password@1", usuario.getPassword());
        assertEquals("John", usuario.getDetalle().getNombre());
    }

}




