package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    // Aqui o en CESTA?
    @PostMapping("/{cestaId}/agregar-a-cesta)")
    public String agregarProductoACesta(@PathVariable Long cestaId,
                                        @RequestParam Long productoId,
                                        @AuthenticationPrincipal Usuario usuario,
                                        HttpServletResponse response,
                                        RedirectAttributes redirectAttributes) throws Exception {
        // Buscar el producto por ID
        Producto producto = productosSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Obtener la cesta por ID y verificar si pertenece al usuario
        Cesta cesta = cestaSrvc.encuentraPorId(cestaId)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        if (!cesta.getUsuario().equals(usuario)) {
            throw new RuntimeException("No tienes permiso para agregar productos a esta cesta.");
        }

        // Crear una cookie con el ID del producto
        Cookie cookie = new Cookie("producto_" + productoId, productoId.toString());
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // La cookie expira en una semana, poner menos/más tiempo?
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", "Producto agregado a la cesta.");
        return "redirect:/cestas/" + cestaId;
    }

    //  FAVORITOS
    // ME HACE LISTA?? o debería ser directamente favoritos?

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