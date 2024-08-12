package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoImagen;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class SrvcImagen extends AbstractSrvc<Imagen, Long, RepoImagen> {
    protected SrvcImagen(RepoImagen repoImagen) {
        super(repoImagen);
    }

    private final Path LOCATION = Paths.get("D:/ficheros");

    public Imagen guardarImagen(MultipartFile file, Supermercado supermercado) throws Exception {
        // Genera un nombre único para la imagen
        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path destino = this.LOCATION.resolve(nombreArchivo);

        // Guarda la imagen en el sistema de archivos
        Files.copy(file.getInputStream(), destino);

        // Crea la entidad Imagen
        Imagen imagen = new Imagen();
        imagen.setNombreArchivo(nombreArchivo);
        imagen.setRutaImagen(destino.toString());  // Establece la ruta completa de la imagen
        imagen.setSupermercado(supermercado);

        // Aquí suponemos que tienes un método para guardar la entidad en la base de datos
        return guardar(imagen);
    }
    public Imagen guardar(Imagen imagen) {
        return getRepo().save(imagen);
    }
}
