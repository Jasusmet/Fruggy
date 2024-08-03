package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import com.eoi.Fruggy.repositorios.RepoGenero;
import com.eoi.Fruggy.repositorios.RepoRol;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcRol;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminUserInitializer implements CommandLineRunner {
    private final RepoUsuario repoUsuario;
    private final RepoRol repoRol;
    private final RepoGenero repoGenero;
    private final RepoDetalle repoDetalle;
    private final PasswordEncoder passwordEncoder;


    public AdminUserInitializer(RepoUsuario repoUsuario, RepoRol repoRol, RepoGenero repoGenero, RepoDetalle repoDetalle, PasswordEncoder passwordEncoder) {
        this.repoUsuario = repoUsuario;
        this.repoRol = repoRol;
        this.repoGenero = repoGenero;
        this.repoDetalle = repoDetalle;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Usuario admin = new Usuario();
        admin.setEmail("admin@example.com");
        admin.setActive(true);
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("Password-123"));

        Detalle detalle = new Detalle();
        detalle.setNombreUsuario("admin");
        detalle.setNombre("Admin");
        detalle.setApellido("User");
        detalle.setEdad(30);
        detalle.setCalle("Calle Admin");
        detalle.setMunicipio("Municipio Admin");
        detalle.setPais("País Admin");
        detalle.setCodigopostal(12345);

        // Asignar un género al detalle
        Genero genero = repoGenero.findById(1).orElseThrow(() -> new RuntimeException("Género no encontrado"));
        detalle.setGenero(genero);

        admin.setDetalle(detalle);

        // Añadir el rol al usuario
        Rol adminRole = repoRol.findByRolNombre("ROLE_ADMIN");
        Set<Rol> roles = new HashSet<>();
        roles.add(adminRole);
        admin.setRoles(roles);

        // Guardar el usuario
        repoUsuario.save(admin);
    }
}
