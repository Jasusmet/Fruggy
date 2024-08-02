package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDonacion;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/donaciones")
public class DonacionCtrl {

    private final SrvcDonacion donacionSrvc;
    private final SrvcUsuario usuarioSrvc;

    public DonacionCtrl(SrvcDonacion donacionSrvc, SrvcUsuario usuarioSrvc) {
        this.donacionSrvc = donacionSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }

    // Crear una nueva donación (GET)
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("donacion", new Donacion());
        return "donaciones/crear-donacion";
    }

    // Crear una nueva donación (POST)
    @PostMapping
    public String crearDonacion(@RequestParam Long usuarioId, @ModelAttribute Donacion donacion) throws Exception {
        Usuario usuario = usuarioSrvc.encuentraPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        donacion.setUsuario(usuario);
        donacion.setFecha(LocalDateTime.now());
        donacionSrvc.guardar(donacion);
        return "redirect:/donaciones";
    }

    // Obtener todas las donaciones
    @GetMapping
    public String listarDonaciones(Model model) {
        List<Donacion> donaciones = donacionSrvc.buscarEntidades();
        model.addAttribute("donaciones", donaciones);
        return "donaciones/donacion-lista";
    }

    // Obtener una donación por ID
    @GetMapping("/{id}")
    public String obtenerDonacion(@PathVariable Long id, Model model) throws Throwable {
        Donacion donacion = (Donacion) donacionSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
        model.addAttribute("donacion", donacion);
        return "donaciones/donacion-detalle";
    }

    // Actualizar una donación (GET)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) throws Throwable {
        Donacion donacion = (Donacion) donacionSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
        model.addAttribute("donacion", donacion);
        return "donaciones/donacion-editar";
    }

    // Actualizar una donación (POST)
    @PostMapping("/{id}")
    public String actualizarDonacion(@PathVariable Long id, @ModelAttribute Donacion donacionActualizada) throws Throwable {
        Donacion donacionExistente = (Donacion) donacionSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
        donacionExistente.setDonacion(donacionActualizada.getDonacion());
        donacionSrvc.guardar(donacionExistente);
        return "redirect:/donaciones";
    }

    // Eliminar una donación (POST)
    @PostMapping("/{id}/eliminar")
    public String eliminarDonacion(@PathVariable Long id) throws Throwable {
        donacionSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
        donacionSrvc.eliminarPorId(id);
        return "redirect:/donaciones";
    }
}
