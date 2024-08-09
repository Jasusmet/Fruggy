package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/productos")
public class ProductoCtrl {

    private final SrvcProducto productosSrvc;
    private final SrvcValProducto valProductosSrvc;
    private final SrvcCesta cestaSrvc;
    private final SrvcLista listaSrvc;
    private final SrvcFavorito favoritoSrvc;
    private final SrvcCategoria categoriaSrvc;
    private final SrvcUsuario usuarioSrvc;

    public ProductoCtrl(SrvcProducto productosSrvc, SrvcValProducto valProductosSrvc, SrvcCesta cestaSrvc, SrvcLista listaSrvc, SrvcFavorito favoritoSrvc, SrvcCategoria categoriaSrvc, SrvcUsuario usuarioSrvc) {
        this.productosSrvc = productosSrvc;
        this.valProductosSrvc = valProductosSrvc;
        this.cestaSrvc = cestaSrvc;
        this.listaSrvc = listaSrvc;
        this.favoritoSrvc = favoritoSrvc;
        this.categoriaSrvc = categoriaSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String mostrarCatalogo(Model model, @AuthenticationPrincipal Usuario usuario) {
        List<Producto> listaProducto = productosSrvc.buscarEntidades(); // Obtener todos los productos
        List<Categoria> categorias = categoriaSrvc.buscarEntidades();
        List<Cesta> cestas = cestaSrvc.buscarEntidades();
        model.addAttribute("listaProducto", listaProducto); // Agregar la lista de productos al modelo
        model.addAttribute("cestas", cestas);
        model.addAttribute("categorias", categorias);
        return "/productos/catalogoProductos";
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detalles/{id}")
    public String verDetallesProducto(@PathVariable("id") Long productoId, Model model) throws Throwable {
        Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + productoId));
        List<Lista> listas = listaSrvc.buscarEntidades();
        List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);

        // Calcular la nota media
        Double notaMedia = valProductosSrvc.calcularNotaMedia(productoId);

        model.addAttribute("producto", producto);
        model.addAttribute("listas", listas);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("notaMedia", notaMedia);
        model.addAttribute("valoracion", new ValoracionProducto());

        return "productos/detalles-producto";
    }

    // hay que añadir el usuario loggeado para que un usuario solo pueda hacer una reseña y que nos muestre quien ha dejado el comentario.
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/valoraciones/{productoId}/guardar")
    public String guardarValoracion(@PathVariable Long  productoId,
                                    @Valid @ModelAttribute("valoracion") ValoracionProducto valoracion,
                                    BindingResult result,
                                    Principal principal,
                                    Model model) throws Throwable {
        if (result.hasErrors()) {
            Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + productoId));
            List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(productoId);
            model.addAttribute("valoraciones", valoraciones);
            model.addAttribute("producto", producto);
            return "redirect:/productos/detalles";
        }

        String username = principal.getName();
        Usuario usuario = usuarioSrvc.getRepo().findByEmail(username);
        valoracion.setUsuario(usuario);

        Producto producto = (Producto) productosSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + productoId));
        valoracion.setProducto(producto);

        valProductosSrvc.guardar(valoracion);
        return "redirect:/productos/detalles/" + productoId;
    }

    // AGREGAR PRODUCTO A CESTA
    // Al agregar a la cesta, este método puede ser llamado desde el formulario
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/agregar-a-cesta")
    public String agregarProductoACesta(@RequestParam Long productoId, @RequestParam Long cestaId, @AuthenticationPrincipal Usuario usuario) throws Throwable {
        // Aregar el producto a la cesta seleccionada
        Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Cesta cesta = (Cesta) cestaSrvc.encuentraPorId(cestaId).orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        CestaProductos cestaProducto = new CestaProductos();
        cestaProducto.setCesta(cesta);
        cestaProducto.setProducto(producto);
        cestaSrvc.guardar(cestaProducto);

        return "redirect:/cestas";
    }

    //  FAVORITOS
//@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/{listaId}/favoritos")
    public String agregarFavorito(@PathVariable Long listaId, @RequestParam Long productoId, Model model) throws Throwable {
        Lista lista = (Lista) listaSrvc.encuentraPorId(listaId).orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + listaId));
        Producto producto = productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productoId));

        Favorito favorito = new Favorito();
        favorito.setLista(lista);
        favorito.setProducto(producto);
        favoritoSrvc.guardar(favorito);

        return "redirect:/listas/" + listaId;
    }


}