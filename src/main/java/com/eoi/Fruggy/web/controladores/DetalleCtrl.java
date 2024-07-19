package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/detalles")
public class DetalleCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;

    @Autowired
    private SrvcGenero generoSrvc;

    @Autowired
    private SrvcUsuario usuarioSrvc;

    @GetMapping("/detalles/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalle = detallesSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            model.addAttribute("detalle", detalle.get());
            List<Genero> generos = generoSrvc.getRepo().findAll();
            model.addAttribute("generos", generos);
            return "detalles";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error"; // Editar `error.html`
        }
    }

    @PostMapping("/detalles/{id}")
    public String guardarDetalle(@PathVariable("id") Long id,
                                 @ModelAttribute Detalle detalleActualizado,
                                 @RequestParam("pathImagen") MultipartFile file,
                                 Model model) {
        Optional<Detalle> detalle = detallesSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            Detalle existente = detalle.get();
            // subir img
            if (!file.isEmpty()) {
                String imagePath = saveImage(file);
                existente.setPathImagen(imagePath);
            }
            // subir datos usuario
            existente.setNombreUsuario(detalleActualizado.getNombreUsuario());
            existente.setNombre(detalleActualizado.getNombre());
            existente.setApellido(detalleActualizado.getApellido());
            existente.setEdad(detalleActualizado.getEdad());
            existente.setDetallesGenero(detalleActualizado.getDetallesGenero());
            try {
                detallesSrvc.guardar(existente);
                model.addAttribute("detalle", existente);
                model.addAttribute("mensaje", "Detalle actualizado correctamente");
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar el detalle: " + e.getMessage());
                return "error";
            }
            return "detalles";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }

    // Metodo para guardar img, debería meterlo en servicio?
    private String saveImage(MultipartFile file) {
        String imagePath = "path/to/save/" + file.getOriginalFilename();
        try {
            File dest = new File(imagePath);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}