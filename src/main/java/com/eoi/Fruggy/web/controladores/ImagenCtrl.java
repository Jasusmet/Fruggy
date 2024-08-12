package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

@RestController
@RequestMapping("/imagenes")
public class ImagenCtrl {
    private final SrvcImagen imagenSrvc;
    private final SrvcSupermercado supermercadoSrvc;

    public ImagenCtrl(SrvcImagen imagenSrvc, SrvcSupermercado supermercadoSrvc) {
        this.imagenSrvc = imagenSrvc;
        this.supermercadoSrvc = supermercadoSrvc;
    }

    @PostMapping("/guardar/{supermercadoId}")
    public ResponseEntity<Imagen> guardarImagen(@PathVariable Long supermercadoId, @RequestParam("imagen") MultipartFile file) {
        try {
            Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId)
                    .orElseThrow(() -> new RuntimeException("Supermercado no encontrado"));

            Imagen imagen = imagenSrvc.guardarImagen(file, supermercado); // Ahora pasando el supermercado
            return ResponseEntity.ok(imagen);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{nombreArchivo}")
    public ResponseEntity<FileSystemResource> obtenerImagen(@PathVariable String nombreArchivo) {
        try {
            // Define la ruta donde se guardan las imágenes
            String rutaDestino = "D:/ficheros/" + nombreArchivo;
            File archivo = new File(rutaDestino);

            if (!archivo.exists()) {
                return ResponseEntity.notFound().build(); // Devuelve 404 si la imagen no se encuentra
            }

            FileSystemResource resource = new FileSystemResource(archivo);
            return ResponseEntity.ok().body(resource); // Devuelve el archivo como recurso
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve 500 en caso de error
        }
    }
}

