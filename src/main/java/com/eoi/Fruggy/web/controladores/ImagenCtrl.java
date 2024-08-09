package com.eoi.Fruggy.web.controladores;


import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/images")
public class ImagenCtrl {

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        File file = new File("D:/files/" + filename);

        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 si no se encuentra
        }

        Resource resource = (Resource) new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}
