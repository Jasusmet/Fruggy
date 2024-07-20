package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.repositorios.RepoImagen;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImagenCtrl {
    @GetMapping("/{filename:.+}")
    public Resource getImage(@PathVariable String filename) {
        File file = new File("D:\\img\\" + filename);
        return (Resource) new FileSystemResource(file);
    }

}
