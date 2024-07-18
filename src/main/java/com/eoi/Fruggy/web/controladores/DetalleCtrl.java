package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcImagen;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Controller
public class DetalleCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;
    @Autowired
    private SrvcGenero generoSrvc;
    @Autowired
    private SrvcImagen imagenSrvc;

    @GetMapping("/detalles/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalle = detallesSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            List<Genero> generos = generoSrvc.buscarEntidades();
            model.addAttribute("detalle", detalle.get());
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
                                 Model model) {
        Optional<Detalle> detalleOptional = detallesSrvc.encuentraPorId(id);
        if (detalleOptional.isPresent()) {
            Detalle existente = detalleOptional.get();

            existente.setNombreUsuario(detalleActualizado.getNombreUsuario());
            existente.setNombre(detalleActualizado.getNombre());
            existente.setApellido(detalleActualizado.getApellido());
            existente.setEdad(detalleActualizado.getEdad());
            existente.setDetallesGenero(detalleActualizado.getDetallesGenero());
            existente.setPathImagen(detalleActualizado.getPathImagen()); // Asignar la URL de la imagen

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
    // Método para guardar la imagen desde una URL
    private void saveImage(String imageUrl) throws IOException {
        // Validar la URL de la imagen (opcional)
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen no puede estar vacía");
        }

        // Obtener el nombre de archivo de la URL
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        // Crear directorio de destino para guardar imagen
        String directoryPath = "/ruta/donde/guardar/las/imagenes";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Crear directorios si no existen
        }

        // Crear un archivo de destino para la imagen
        File targetFile = new File(directoryPath + File.separator + fileName);

        // Descargar la imagen desde la URL y guardarla en el archivo de destino
        FileUtils.copyURLToFile(new URL(imageUrl), targetFile);
    }


}
