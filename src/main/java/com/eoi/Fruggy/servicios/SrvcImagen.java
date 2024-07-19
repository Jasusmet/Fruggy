package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.repositorios.RepoImagen;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Service
public class SrvcImagen extends AbstractSrvc<Imagen, Long, RepoImagen>{
    protected SrvcImagen(RepoImagen repoImagen) {
        super(repoImagen);
    }
    public String guardarImagen(MultipartFile file) throws IOException {
        String directory = "path/to/save/";
        String ruta = directory + file.getOriginalFilename();
        File dest = new File(ruta);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        file.transferTo(dest);
        return ruta;
    }

}
