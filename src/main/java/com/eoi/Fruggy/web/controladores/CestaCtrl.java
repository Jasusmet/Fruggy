package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcCesta;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping
    public String listarCestas(@AuthenticationPrincipal Usuario usuario, Model model) {
        List<Cesta> cestas = cestaSrvc.getRepo().findAll();
        model.addAttribute("cestas", cestas);
        model.addAttribute("usuario", usuario);
        return "cestas/cesta-lista";
    }

    // Crear una nueva cesta (GET)
    @GetMapping("/crear")
    public String mostrarFormularioCrearCesta(Model model) {
        model.addAttribute("cesta", new Cesta());
        return "cestas/crear-cesta";
    }
    // Crear una nueva cesta (POST)
    @PostMapping("/crear")
    public String crearCesta(@ModelAttribute Cesta cesta, @AuthenticationPrincipal Usuario usuario, RedirectAttributes redirectAttributes) throws Exception {
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
    @GetMapping("/{id}")
    public String obtenerCesta(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        model.addAttribute("cesta", cesta);
        return "/cestas/cesta-detalle"; // Nombre de la vista para mostrar detalles de la cesta
    }
    // Actualizar una cesta (GET)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) throws Throwable {
        Cesta cesta = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        model.addAttribute("cesta", cesta);
        return "/cestas/cesta-editar";
    }
    // Actualizar una cesta (POST)
    @PostMapping("/{id}")
    public String actualizarCesta(@PathVariable Long id, @ModelAttribute Cesta cestaActualizada) throws Throwable {
        Cesta cestaExistente = (Cesta) cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaExistente.setNombre(cestaActualizada.getNombre());
        cestaSrvc.guardar(cestaExistente);
        return "redirect:/cestas";
    }
    // Eliminar una cesta (POST)
    @PostMapping("/{id}/eliminar")
    public String eliminarCesta(@PathVariable Long id) throws Throwable {
        cestaSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Cesta no encontrada"));
        cestaSrvc.eliminarPorId(id);
        return "redirect:/cestas";
    }

}
