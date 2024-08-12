package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.repositorios.RepoImagen;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Service
public class SrvcImagen extends AbstractSrvc<Imagen, Long, RepoImagen> {
    protected SrvcImagen(RepoImagen repoImagen) {
        super(repoImagen);
    }

    public Imagen guardarImagen(MultipartFile file, Supermercado supermercado) throws Exception {
        String nombreArchivo = file.getOriginalFilename();
        String rutaDestino = "D:/ficheros/" + nombreArchivo;
        // Guarda el archivo en la ruta especificada
        try {
            file.transferTo(new File(rutaDestino));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Imagen imagen = new Imagen();
        imagen.setNombreArchivo(nombreArchivo);
        imagen.setSupermercado(supermercado);

        // Guarda la imagen en la base de datos
        return getRepo().save(imagen);
    }
}
