package com.eoi.Fruggy.web.controladores;


import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/images")
public class ImagenCtrl {
    @GetMapping("/{filename:.+}")
    public Resource getImage(@PathVariable String filename) {
        File file = new File("D:\\img\\" + filename);
        return (Resource) new FileSystemResource(file);
    }
}
