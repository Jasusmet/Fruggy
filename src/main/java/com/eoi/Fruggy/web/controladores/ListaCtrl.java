package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.entidades.Lista;
import com.eoi.Fruggy.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/listas")
public class ListaCtrl {

    private final SrvcLista listaSrvc;
    private final SrvcUsuario usuarioSrvc;
    private final SrvcFavorito favoritoSrvc;
    private final SrvcPrecio precioSrvc;
    private final SrvcProducto productoSrvc;

    public ListaCtrl(SrvcLista listaSrvc, SrvcUsuario usuarioSrvc, SrvcFavorito favoritoSrvc, SrvcPrecio precioSrvc, SrvcProducto productoSrvc) {
        this.listaSrvc = listaSrvc;
        this.usuarioSrvc = usuarioSrvc;
        this.favoritoSrvc = favoritoSrvc;
        this.precioSrvc = precioSrvc;
        this.productoSrvc = productoSrvc;
    }

    @GetMapping
    public String listarListas(Model model) {
        List<Lista> listaListas = listaSrvc.buscarEntidades();
        model.addAttribute("listas", listaListas);
        return "/listas/lista-listas";
    }
    @GetMapping("/crear-lista")
    public String mostrarFormularioLista(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("lista", new Lista());
        model.addAttribute("usuarios", usuarioSrvc.buscarEntidades());
        model.addAttribute("usuario", usuario);
        return "listas/crear-lista";
    }
    @PostMapping
    public String guardarLista(@ModelAttribute Lista lista, @AuthenticationPrincipal Usuario usuario, Model model) throws Exception {
        // Verifica si el usuario tiene el número máximo de listas
        if (usuario.getListas().size() >= 10) {
            model.addAttribute("error", "El usuario ya tiene el número máximo de listas permitidas (10).");
            model.addAttribute("usuarios", usuarioSrvc.buscarEntidades());
            return "listas/crear-lista";
        }

        // Asigna el usuario a la lista y guarda la lista
        lista.setUsuario(usuario);
        lista.setFechaLista(LocalDateTime.now());
        listaSrvc.guardar(lista);

        return "redirect:/listas";
    }
    @GetMapping("/{id}")
    public String verDetallesLista(@PathVariable Long id, Model model) throws Throwable {
        Lista lista = (Lista) listaSrvc.encuentraPorId(id).orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + id));
        model.addAttribute("lista", lista);
        model.addAttribute("favoritos", lista.getFavoritos());
        model.addAttribute("precios", precioSrvc.buscarEntidades());
        return "listas/detalles-lista";
    }
    @PostMapping("/{id}/favoritos")
    public String agregarFavoritoDesdeProducto(@PathVariable Long id, @RequestParam Long productoId) throws Throwable {
        // Obtiene la lista por ID
        Lista lista = (Lista) listaSrvc.encuentraPorId(id).orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + id));
        // Obtiene el producto por ID
        Producto producto = productoSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productoId));

        // Crea un nuevo favorito y lo guarda
        Favorito favorito = new Favorito();
        favorito.setLista(lista);
        favorito.setProducto(producto);
        favoritoSrvc.guardar(favorito);

        return "redirect:/listas/" + id; // Redirige a la vista de detalles de la lista
    }
    @DeleteMapping("/{id}/favoritos/{favoritoId}")
    public String eliminarFavorito(@PathVariable Long id, @PathVariable Long favoritoId) throws Throwable {
        Favorito favorito = (Favorito) favoritoSrvc.encuentraPorId(favoritoId).orElseThrow(() -> new IllegalArgumentException("Favorito no encontrado: " + favoritoId));
        favoritoSrvc.eliminarPorId(favoritoId);
        return "redirect:/listas/" + id;
    }

    @DeleteMapping("/{id}")
    public String eliminarLista(@PathVariable Long id) throws Throwable {
        listaSrvc.eliminarPorId(id);
        return "redirect:/listas";
    }

}
