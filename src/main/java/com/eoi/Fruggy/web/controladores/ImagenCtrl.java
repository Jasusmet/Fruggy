package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.repositorios.RepoImagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/imagenes")
public class ImagenCtrl {

    @Autowired
    private RepoImagen repoImagen;

    @PostMapping("/subir")
    public String subirImagen(@RequestParam("imagen") MultipartFile file,
                              @RequestParam("usuarioId") Long usuarioId,
                              @RequestParam("supermercadoId") Long supermercadoId,
                              @RequestParam("productoId") Long productoId,
                              Model model) {
        String ruta = guardarImagen(file);
        if (ruta != null) {
            Imagen imagen = new Imagen();
            imagen.setNombreArchivo(file.getOriginalFilename());
            imagen.setRuta(ruta);
            try {
                repoImagen.save(imagen);
                model.addAttribute("mensaje", "Imagen subida correctamente");
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar la imagen");
            }
        } else {
            model.addAttribute("error", "Error al guardar el archivo");
        }
        return "resultado";
    }

    private String guardarImagen(MultipartFile file) {
        String directory = "/path/to/save/images/";
        String ruta = directory + file.getOriginalFilename();
        try {
            File dest = new File(ruta);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ruta;
    }
}
