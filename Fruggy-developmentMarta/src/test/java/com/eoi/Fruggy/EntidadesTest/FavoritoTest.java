package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Favorito;
import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.repositorios.RepoFavorito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FavoritoTest {
    @Autowired
    private RepoFavorito repoFavorito;
//    @Autowired
//    private RepoPrecio repoPrecio;
//    @Autowired
//    private RepoLista repoLista;

    @Test
    public void test() {
        Precio precio = new Precio();
//        precio = repoPrecio.save(precio);

        Lista lista = new Lista();
//        lista = repoLista.save(lista);

        Favorito favorito = new Favorito();
        favorito.setPrecioFavoritos(precio);
        favorito.setListaFavoritos(lista);

        Favorito guardarFavorito = repoFavorito.save(favorito);
        Optional<Favorito> favoritoRecuperado = repoFavorito.findById((int) guardarFavorito.getId());


        assertNotNull(favoritoRecuperado.get().getId());
        assertNotNull(favoritoRecuperado.get().getPrecioFavoritos());
//        assertEquals(precio.getId(), favoritoRecuperado.get().getPrecioFavoritos().getId());
        assertNotNull(favoritoRecuperado.get().getListaFavoritos());
//        assertEquals(lista.getId(), favoritoRecuperado.get().getListaFavoritos().getId());


    }
}
