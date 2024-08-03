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


    public ProductoCtrl(SrvcProducto productosSrvc, SrvcValProducto valProductosSrvc, SrvcCesta cestaSrvc, SrvcLista listaSrvc, SrvcFavorito favoritoSrvc) {
        this.productosSrvc = productosSrvc;
        this.valProductosSrvc = valProductosSrvc;
        this.cestaSrvc = cestaSrvc;
        this.listaSrvc = listaSrvc;
        this.favoritoSrvc = favoritoSrvc;
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String mostrarCatalogo(Model model, @AuthenticationPrincipal Usuario usuario) {
        List<Producto> listaProducto = productosSrvc.buscarEntidades(); // Obtener todos los productos
        List<Cesta> cestas = cestaSrvc.buscarEntidades();
        model.addAttribute("listaProducto", listaProducto); // Agregar la lista de productos al modelo
        model.addAttribute("cestas", cestas);
        return "/productos/catalogoProductos";
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detalles/{id}")
    public String verDetallesProducto(@PathVariable("id") Long id, Model model) throws Throwable {
        Producto producto = productosSrvc.encuentraPorId(id).orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + id));
        List<Lista> listas = listaSrvc.buscarEntidades();
        List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(producto.getPrecios().iterator().next().getId());

        model.addAttribute("producto", producto);
        model.addAttribute("listas", listas);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("valoracion", new ValoracionProducto());

        // Calcular la nota media
        double notaMedia = valoraciones.stream()
                .mapToDouble(ValoracionProducto::getNota)
                .average()
                .orElse(0);
        model.addAttribute("notaMedia", notaMedia);

        return "productos/detalles-producto";
    }

    // hay que añadir el usuario loggeado para que un usuario solo pueda hacer una reseña y que nos muestre quien ha dejado el comentario.
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/valoraciones/producto/{id}/guardar")
    public String guardarValoracion(@PathVariable Long id,
                                    @Valid @ModelAttribute("valoracion") ValoracionProducto valoracion,
                                    BindingResult result,
                                    Principal principal) throws Throwable {
        if (result.hasErrors()) {
            return "productos/detalles-producto";
        }
        Producto producto = productosSrvc.encuentraPorId(id).orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + id));
        valoracion.setProducto(producto);
        valProductosSrvc.guardar(valoracion);
        return "redirect:/productos/detalles/" + id;
    }

    // AGREGAR PRODUCTO A CESTA
    // Al agregar a la cesta, este método puede ser llamado desde el formulario
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/agregar-a-cesta")
    public String agregarProductoACesta(@RequestParam Long productoId, @RequestParam Long cestaId, @AuthenticationPrincipal Usuario usuario) throws Throwable {
        // Aregar el producto a la cesta seleccionada
        Producto producto =productosSrvc.encuentraPorId(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
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