package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.Donacion;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDonacion;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Obtener todas las donaciones
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String listarDonaciones(Model model) {
        List<Donacion> donaciones = donacionSrvc.buscarEntidades();
        model.addAttribute("donaciones", donaciones);
        return "donaciones/donacion-lista";
    }

    // Crear una nueva donación (GET)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("donacion", new Donacion());
        return "donaciones/crear-donacion";
    }

    // Crear una nueva donación (POST)
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public String crearDonacion(@RequestParam Long usuarioId, @ModelAttribute Donacion donacion) throws Exception {
        Usuario usuario = usuarioSrvc.encuentraPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        donacion.setUsuario(usuario);
        donacion.setFecha(LocalDateTime.now());
        donacionSrvc.guardar(donacion);
        return "redirect:/donaciones";
    }

    // Obtener una donación por ID
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public String obtenerDonacion(@PathVariable Long id, Model model) throws Throwable {
        Donacion donacion = (Donacion) donacionSrvc.encuentraPorId(id)
                .orElseThrow(() -> new RuntimeException("Donación no encontrada"));
        model.addAttribute("donacion", donacion);
        return "donaciones/donacion-detalle";
    }
}
