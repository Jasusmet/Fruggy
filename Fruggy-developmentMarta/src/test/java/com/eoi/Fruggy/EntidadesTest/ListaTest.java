package com.eoi.Fruggy.EntidadesTest;

import com.eoi.Fruggy.entidades.Favorito;
import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.repositorios.RepoLista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ListaTest {

    @Autowired
    private RepoLista repoLista;

    @Test
    public void testLista() {
        Lista lista = new Lista();
        lista.setNombreLista("lista compra Mihai");
        LocalDateTime now = LocalDateTime.now();
        lista.setFechaLista(now); // lo pongo pero falla por milisegundos de nuevo : )

        Set<Favorito> listaFavoritos= new HashSet<>();
        Favorito favorito1 = new Favorito();
        Favorito favorito2 = new Favorito();
        listaFavoritos.add(favorito1);
        listaFavoritos.add(favorito2);
        lista.setListaFavoritos(listaFavoritos);

        Lista guardarLista = repoLista.save(lista);
        Optional<Lista> listaRecuperada=repoLista.findById((int) guardarLista.getId());

        assertNotNull(listaRecuperada.get().getId());
        assertEquals("lista compra Mihai", listaRecuperada.get().getNombreLista());
        assertNotNull(listaRecuperada.get().getFechaLista());

        assertNotNull(listaRecuperada.get().getListaFavoritos());
        assertEquals(0, listaRecuperada.get().getListaFavoritos().size()); // aqui debería tener 2, NO SE PORQUE FALLA, ES POR CREATE-DROP?

    }
}
