package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Precio;
import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcCesta;
import com.eoi.Fruggy.servicios.SrvcPrecio;
import com.eoi.Fruggy.servicios.SrvcProducto;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cestas")
public class CestaCtrl {

    private final SrvcCesta cestaSrvc;
    private final SrvcUsuario usuarioSrvc;
    private final SrvcProducto productoSrvc;
    private final SrvcPrecio precioSrvc;


    public CestaCtrl(SrvcCesta cestaSrvc, SrvcUsuario usuarioSrvc, SrvcProducto productoSrvc, SrvcPrecio precioSrvc) {
        this.cestaSrvc = cestaSrvc;
        this.usuarioSrvc = usuarioSrvc;
        this.productoSrvc = productoSrvc;
        this.precioSrvc = precioSrvc;
    }

    //Listar cestas usuarios
//        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String listarCestas(@AuthenticationPrincipal Usuario usuario, Model model) {
        List<Cesta> cestas = cestaSrvc.findByUsuario(usuario);
        model.addAttribute("cestas", cestas);
        model.addAttribute("usuario", usuario);
        return "cestas/cesta-lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearCesta(Model model) {
        Cesta cesta = new Cesta();
        List<Cesta> cestas = cestaSrvc.buscarEntidades();
        model.addAttribute("cesta", cesta);
        return "cestas/crear-cesta";
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/guardar")
    public String guardarCesta(@Valid @ModelAttribute Cesta cesta,
                               BindingResult result,
                               @AuthenticationPrincipal Usuario usuario,
                               RedirectAttributes redirectAttributes,
                               @RequestParam(value = "productoId", required = false) Long productoId) throws Exception {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Errores en la creación de la cesta.");
            return "redirect:/cestas/crear";
        }

        List<Cesta> cestasUsuario = cestaSrvc.findByUsuario(usuario);
        if (cestasUsuario.size() >= 10) {
            redirectAttributes.addFlashAttribute("error", "No puedes tener más de 10 cestas.");
            return "redirect:/cestas";
        }

        cesta.setUsuario(usuario);
        cesta.setFecha(LocalDateTime.now());

        cestaSrvc.guardar(cesta);
        redirectAttributes.addFlashAttribute("success", "Cesta creada con éxito.");

        if (productoId != null) {
            return "redirect:/productos?agregarACesta=true&cestaId=" + cesta.getId();
        }

        return "redirect:/cestas";
    }

    // Obtener una cesta por ID
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public String obtenerCesta(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        // Asumiendo que tienes una forma de obtener los productos con detalles
        List<Producto> productosConDetalles = new ArrayList<>();
        for (Producto producto : cesta.getProductos()) {
            // Cargar detalles adicionales como descuentos y supermercado
            Producto productoConDetalles = productoSrvc.encuentraPorId(producto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            productosConDetalles.add(productoConDetalles);
        }

        model.addAttribute("cesta", cesta);
        model.addAttribute("productos", productosConDetalles);
        return "cestas/cesta-detalle";
    }

    // Actualizar una cesta (GET)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        model.addAttribute("cesta", cesta);
        return "/cestas/cesta-editar";
    }

    // Actualizar una cesta (POST)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/{id}")
    public String actualizarCesta(@PathVariable Long id,
                                  @Valid @ModelAttribute Cesta cestaActualizada,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal Usuario usuario) throws Throwable {
        // Validar el objeto Cesta
        if (bindingResult.hasErrors()) {
            return "/cestas/cesta-editar"; // Volver al formulario si hay errores
        }

        Cesta cestaExistente = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        // Verificar si la cesta pertenece al usuario
        if (!cestaExistente.getUsuario().equals(usuario)) {
            throw new RuntimeException("No tienes permiso para editar esta cesta.");
        }

        cestaExistente.setNombre(cestaActualizada.getNombre());
        cestaSrvc.guardar(cestaExistente);
        return "redirect:/cestas";
    }

    // Eliminar una cesta (POST)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/{id}/eliminar")
    public String eliminarCesta(@PathVariable Long id,
                                @AuthenticationPrincipal Usuario usuario) throws Throwable {
        Cesta cestaExistente = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));

        // Verificar si la cesta pertenece al usuario
        if (!cestaExistente.getUsuario().equals(usuario)) {
            throw new RuntimeException("No tienes permiso para eliminar esta cesta.");
        }

        cestaSrvc.eliminarPorId(id);
        return "redirect:/cestas";
    }

    /// AGREGAR PRODUCTO A CESTA (COOKIES)
    @PostMapping("/agregarProducto")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String agregarProductoACesta(@RequestParam Long productoId,
                                        @RequestParam(required = false) Long cestaId,
                                        @RequestParam(required = false) String nuevaCesta,
                                        @RequestParam Integer cantidad,
                                        @AuthenticationPrincipal Usuario usuario,
                                        RedirectAttributes redirectAttributes) throws Exception {

        Producto producto = productoSrvc.encuentraPorId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (cestaId != null) {
            cestaSrvc.agregarProductoACesta(cestaId, productoId, cantidad, null);
        } else if (nuevaCesta != null && !nuevaCesta.isEmpty()) {
            Cesta cesta = new Cesta();
            cesta.setNombre(nuevaCesta);
            cesta.setUsuario(usuario);
            cesta.setFecha(LocalDateTime.now());
            cesta.addProducto(producto, cantidad, null);
            cestaSrvc.guardar(cesta);
        } else {
            redirectAttributes.addFlashAttribute("error", "Debes seleccionar una cesta existente o crear una nueva.");
            return "redirect:/productos";
        }

        redirectAttributes.addFlashAttribute("success", "Producto agregado a la cesta con éxito.");
        return "redirect:/productos";
    }
    @PostMapping("/{cestaId}/eliminarProducto")
    public String eliminarProductoDeCesta(@PathVariable Long cestaId,
                                          @RequestParam Long productoId,
                                          RedirectAttributes redirectAttributes) {
        try {
            cestaSrvc.eliminarProductoDeCesta(cestaId, productoId);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado de la cesta con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el producto de la cesta.");
        }
        return "redirect:/cestas/{cestaId}";
    }
}
