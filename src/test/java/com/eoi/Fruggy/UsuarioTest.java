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
        // Crear los roles asociados al usuario
        Rol rol = new Rol();
        rol.setRolNombre("USER");
        rol.setEnDesc("User Role");
        rol.setEsDesc("Rol de Usuario");
        repoRol.save(rol);

        // Crear los detalles del usuario
        Detalle detalle = new Detalle();
        detalle.setNombre("John");
        detalle.setApellido("Doe");


        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setEmail("johndoe@example.com");
        usuario.setPassword("Password@1");
        usuario.setTelefono("1234567890");
        usuario.setDetalle(detalle);
        usuario.setActive(true);

        // Asignar roles al usuario
        usuario.getRoles().add(rol);

        // Guardar el usuario
        repoUsuario.save(usuario);

        // Verificar que el usuario se ha guardado correctamente
        Optional<Usuario> usuarioOptional = repoUsuario.findById(usuario.getId());
        assertTrue(usuarioOptional.isPresent());

        Usuario usuarioGuardado = usuarioOptional.get();

        assertEquals("johndoe@example.com", usuarioGuardado.getEmail());
        assertEquals("1234567890", usuarioGuardado.getTelefono());
        assertEquals("Password@1", usuarioGuardado.getPassword());
        assertEquals("John", usuarioGuardado.getDetalle().getNombre());
        assertEquals("USER", usuarioGuardado.getRoles().iterator().next().getRolNombre());

    }

    }




