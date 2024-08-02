package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
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


    public ProductoCtrl(SrvcProducto productosSrvc, SrvcValProducto valProductosSrvc) {
        this.productosSrvc = productosSrvc;
        this.valProductosSrvc = valProductosSrvc;
    }

    @GetMapping
    public String mostrarCatalogo(Model model) {
        List<Producto> listaProducto = productosSrvc.buscarEntidades(); // Obtener todos los productos
        model.addAttribute("listaProducto", listaProducto); // Agregar la lista de productos al modelo
        return "/productos/catalogoProductos";
    }

    @GetMapping("/detalles/{id}")
    public String verDetallesProducto(@PathVariable("id") Long id, Model model) throws Throwable {
        Producto producto = productosSrvc.encuentraPorId(id).orElseThrow(() -> new IllegalArgumentException("ID de producto inválido:" + id));
        List<ValoracionProducto> valoraciones = valProductosSrvc.obtenerValoracionesPorProducto(producto.getPrecios().iterator().next().getId());

        model.addAttribute("producto", producto);
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
}