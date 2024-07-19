package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SupermercadoCtrl {

    @Autowired
    private SrvcSupermercado supermercadosSrvc;


    @GetMapping("/supermercados/{id}")
    public String verSupermercado(@PathVariable("id") Long id, Model model) {
        Optional<Supermercado> supermercado = supermercadosSrvc.encuentraPorId(id);
        if (supermercado.isPresent()) {
            model.addAttribute("supermercado", supermercado.get());
            return "supermercado";
        } else {
            model.addAttribute("error", "Supermercado no encontrado");
            return "error"; // Editar `error.html`
        }
    }

    @PostMapping("/supermercados/{id}")
    public String guardarSupermercado(@PathVariable("id") Long id,
                                      @ModelAttribute Supermercado supermercadoActualizado,
                                      @RequestParam("imagenPath") MultipartFile file,
                                      Model model) {
        Optional<Supermercado> supermercado = supermercadosSrvc.encuentraPorId(id);
        if (supermercado.isPresent()) {
            Supermercado existente = supermercado.get();
            // Manejar subida de imagen
            if (!file.isEmpty()) {
                try {
                    String imagePath = saveImage(file);
                    existente.setImagenPath(imagePath);
                } catch (IOException e) {
                    model.addAttribute("error", "Error al guardar la imagen: " + e.getMessage());
                    return "error";
                }
            }
            // Actualizar datos del supermercado
            existente.setNombreSuper(supermercadoActualizado.getNombreSuper());
            existente.setDireccion(supermercadoActualizado.getDireccion());
            try {
                supermercadosSrvc.guardar(existente);
                model.addAttribute("supermercado", existente);
                model.addAttribute("mensaje", "Supermercado actualizado correctamente");
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar el supermercado: " + e.getMessage());
                return "error";
            }
            return "supermercado";
        } else {
            model.addAttribute("error", "Supermercado no encontrado");
            return "error";
        }
    }


    private String saveImage(MultipartFile file) throws IOException {
        String directory = "path/to/save/";
        String imagePath = directory + file.getOriginalFilename();
        File dest = new File(imagePath);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        return imagePath;
    }

}

