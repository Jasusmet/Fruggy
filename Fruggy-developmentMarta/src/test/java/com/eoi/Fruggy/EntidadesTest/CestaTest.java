package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoCesta;
import com.eoi.Fruggy.repositorios.RepoPrecio;
import com.eoi.Fruggy.repositorios.RepoProducto;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CestaTest {
    @Autowired
    private RepoCesta repoCesta;
    @Autowired
    private RepoUsuario repoUsuarios;
    @Autowired
    private RepoPrecio repoPrecio;
    @Autowired
    private RepoProducto repoProducto;

    @Test
    public void testCesta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario1@gmail.com");
        usuario = repoUsuarios.save(usuario);

        // Buscamos producto
        Optional<Producto> producto = repoProducto.findProductoByNombreProductoAndMarca("Leche", "Puleva");
        if (producto.isPresent()) {
            Producto p = producto.get();
        }else {
            throw new Exception("Error en el test");
        }

        Precio precio = new Precio();
        precio.setActivo(true);
        precio.setPrecioProductos(producto.get());
        precio = repoPrecio.save(precio);

        Cesta cesta = new Cesta();
        cesta.setFecha(LocalDateTime.now());
        cesta.setCestaUsuario(usuario);
        cesta.setPreciosCesta(precio);


       Cesta cestaGuardada = repoCesta.save(cesta);
       // Optional<Cesta> cestaRecuperada = repoCesta.findById((int) cestaGuardada.getId());

        assertNotNull(cestaGuardada.getId());
        assertNotNull(cestaGuardada.getFecha());
        assertEquals(usuario.getId(),cestaGuardada.getCestaUsuario().getId());
        assertEquals(precio.getId(),cestaGuardada.getPreciosCesta().getId());

        // Definitivamente el programa falla porque las tablas no existen !!! o eso creo :)

    }
}
