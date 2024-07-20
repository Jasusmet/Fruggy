package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import lombok.extern.slf4j.Slf4j;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;

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
            model.addAttribute("generos", generoSrvc.buscarEntidades());
            return "detalles";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error"; // Editar `error.html`
        }
    }

    @PostMapping("/detalles/{id}")
    public String guardarDetalle(@PathVariable("id") Long id,
                                 @ModelAttribute Detalle detalleActualizado,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestParam("generoId") Long generoId,
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

            // Establecer el género
            Optional<Genero> genero = generoSrvc.getRepo().findById(generoId);
            if (genero.isPresent()) {
                existente.setDetallesGenero(genero.get());
            } else {
                model.addAttribute("error", "Género no encontrado");
                return "error";
            }

            // Campos de dirección
            existente.setCalle(detalleActualizado.getCalle());
            existente.setMunicipio(detalleActualizado.getMunicipio());
            existente.setPais(detalleActualizado.getPais());
            existente.setCodigopostal(detalleActualizado.getCodigopostal());

            // Manejo de la imagen
            if (file != null && !file.isEmpty()) {
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
        return imagePath;
    }
}