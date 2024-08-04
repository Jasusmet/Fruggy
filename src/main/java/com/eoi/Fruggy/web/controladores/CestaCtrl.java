package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcCesta;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cestas")
public class CestaCtrl {

    private final SrvcCesta cestaSrvc;
    private final SrvcUsuario usuarioSrvc;


    public CestaCtrl(SrvcCesta cestaSrvc, SrvcUsuario usuarioSrvc) {
        this.cestaSrvc = cestaSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }
        //Listar cestas usuarios
//        @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String listarCestas(@AuthenticationPrincipal Usuario usuario, Model model) {
        List<Cesta> cestas = cestaSrvc.getRepo().findAll();
        model.addAttribute("cestas", cestas);
        model.addAttribute("usuario", usuario);
        return "cestas/cesta-lista";
    }

    // Crear una nueva cesta (GET)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/crear")
    public String mostrarFormularioCrearCesta(Model model) {
        model.addAttribute("cesta", new Cesta());
        return "cestas/crear-cesta";
    }
    // Crear una nueva cesta (POST)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/crear")
    public String crearCesta(@Valid @ModelAttribute Cesta cesta,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal Usuario usuario,
                             RedirectAttributes redirectAttributes) throws Exception {
        // Validar el objeto Cesta
        if (bindingResult.hasErrors()) {
            return "cestas/crear-cesta"; // Volver al formulario si hay errores
        }

        if (usuario.getCestas().size() < 10) {
            cesta.setUsuario(usuario);
            cesta.setFecha(LocalDateTime.now());
            cestaSrvc.guardar(cesta);
            redirectAttributes.addFlashAttribute("success", "Cesta creada exitosamente.");
            return "redirect:/cestas";
        }
        redirectAttributes.addFlashAttribute("error", "No puedes crear más de 10 cestas.");
        return "redirect:/cestas";
    }

    // Obtener una cesta por ID
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public String obtenerCesta(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        model.addAttribute("cesta", cesta);
        return "/cestas/cesta-detalle"; // Nombre de la vista para mostrar detalles de la cesta
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
                                  BindingResult bindingResult) throws Throwable {
        // Validar el objeto Cesta
        if (bindingResult.hasErrors()) {
            return "/cestas/cesta-editar"; // Volver al formulario si hay errores
        }
        Cesta cestaExistente = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaExistente.setNombre(cestaActualizada.getNombre());
        cestaSrvc.guardar(cestaExistente);
        return "redirect:/cestas";
    }
    // Eliminar una cesta (POST)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/{id}/eliminar")
    public String eliminarCesta(@PathVariable Long id) throws Throwable {
        cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaSrvc.eliminarPorId(id);
        return "redirect:/cestas";
    }

}
