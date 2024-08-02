package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Cesta;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcCesta;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Crear una nueva cesta (GET)
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("cesta", new Cesta());
        return "/cestas/crear-cesta";
    }

    // Crear una nueva cesta (POST)
    @PostMapping
    public String crearCesta(@RequestParam Long usuarioId, @ModelAttribute Cesta cesta) throws Exception {
        Usuario usuario = usuarioSrvc.encuentraPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        cesta.setUsuario(usuario);
        cesta.setFecha(LocalDateTime.now());
        cestaSrvc.guardar(cesta);
        return "redirect:/cestas";
    }
    // Obtener todas las cestas
    @GetMapping
    public String listarCestas(Model model) {
        List<Cesta> cestas = cestaSrvc.buscarEntidades();
        model.addAttribute("cestas", cestas);
        return "/cestas/cesta-lista"; // hay que crear todos los html
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
