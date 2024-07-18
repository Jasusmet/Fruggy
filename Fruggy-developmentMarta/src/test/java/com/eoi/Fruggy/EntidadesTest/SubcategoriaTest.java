package com.eoi.Fruggy.EntidadesTest;
import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.repositorios.RepoSubcategoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class SubcategoriaTest {

    @Autowired
    private RepoSubcategoria repoSubcategoria;

    @Test
    public void testSubcategoria() {
        Subcategoria subcategoria = new Subcategoria();
        Producto producto = new Producto();
        Categoria categoria = new Categoria();


        subcategoria.setSubcategoriaProducto(producto);
        producto.setNombreProducto("Hamburguesa eco");
        subcategoria.setSubcategoriaCategoria(categoria);
        categoria.setTipo("Carnes");

        Subcategoria guardarSubcategoria = repoSubcategoria.save(subcategoria);
        Optional<Subcategoria> subcategoriaRecuperada = repoSubcategoria.findById((int) guardarSubcategoria.getId());

        assertNotNull(subcategoriaRecuperada.get().getSubcategoriaProducto());
        assertEquals("Hamburguesa eco", subcategoriaRecuperada.get().getSubcategoriaProducto().getNombreProducto());
        assertNotNull(subcategoriaRecuperada.get().getSubcategoriaCategoria());
        assertEquals("Carnes", subcategoriaRecuperada.get().getSubcategoriaCategoria().getTipo());
    }
}
