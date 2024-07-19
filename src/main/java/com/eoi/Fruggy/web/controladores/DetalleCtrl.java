package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class DetalleCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;
    @Autowired
    private SrvcGenero generoSrvc;
    @Autowired
    private SrvcImagen imagenSrvc;
    @Autowired
    private SrvcRol rolSrvc;
    @Autowired
    private SrvcUsuario usuarioSrvc;

    @GetMapping("/detalles/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalle = detallesSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            model.addAttribute("detalle", detalle.get());
            model.addAttribute("generos", generoSrvc.buscarEntidades());
            model.addAttribute("roles", rolSrvc.buscarEntidades());
            model.addAttribute("usuarios", usuarioSrvc.buscarEntidades());
            return "detalles";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }

    @PostMapping("/detalles/{id}")
    public String guardarDetalle(@PathVariable("id") Long id,
                                 @ModelAttribute Detalle detalleActualizado,
                                 @RequestParam("file") MultipartFile file,
                                 Model model) throws Exception {
        Optional<Detalle> detalleOptional = detallesSrvc.encuentraPorId(id);
        if (detalleOptional.isPresent()) {
            Detalle existente = detalleOptional.get();
            // Establecer los valores actualizados
            existente.setNombreUsuario(detalleActualizado.getNombreUsuario());
            existente.setNombre(detalleActualizado.getNombre());
            existente.setApellido(detalleActualizado.getApellido());
            existente.setEdad(detalleActualizado.getEdad());
            existente.setDetallesGenero(detalleActualizado.getDetallesGenero());

            // Manejo de la imagen
            if (!file.isEmpty()) {
                try {
                    // Guardar la imagen en el servidor
                    String directoryPath = "D:\\img";
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs(); // Crear directorios si no existen
                    }
                    String fileName = file.getOriginalFilename();
                    File targetFile = new File(directoryPath + File.separator + fileName);
                    file.transferTo(targetFile);

                    // Crear o actualizar la entidad Imagen
                    Imagen imagen = existente.getImagen();
                    if (imagen == null) {
                        imagen = new Imagen();
                        imagen.setDetalle(existente);
                    }
                    imagen.setNombreArchivo(fileName);
                    imagen.setRuta(directoryPath);
                    imagen.setPathImagen("/images/" + fileName); // URL relativa para acceder a la imagen

                    // Guardar la imagen y establecer la relación bidireccional
                    imagenSrvc.guardar(imagen);
                    existente.setImagen(imagen);

                } catch (IOException e) {
                    model.addAttribute("error", "Error al guardar la imagen: " + e.getMessage());
                    return "error";
                }
            }

            // Guardar el detalle actualizado
            try {
                detallesSrvc.guardar(existente);
                model.addAttribute("detalle", existente);
                model.addAttribute("mensaje", "Detalle actualizado correctamente");
                return "redirect:/detalles-actualizados/" + existente.getId();
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar el detalle: " + e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }

    @GetMapping("/detalles-actualizados/{id}")
    public String verDetallesActualizados(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalleActualizado = detallesSrvc.encuentraPorId(id);
        if (detalleActualizado.isPresent()) {
            model.addAttribute("detalle", detalleActualizado.get());
            return "detalles-actualizados";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }
}