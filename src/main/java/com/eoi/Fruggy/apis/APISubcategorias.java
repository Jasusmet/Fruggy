package com.eoi.Fruggy.apis;

import com.eoi.Fruggy.DTO.SubcategoriaDTO;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.servicios.SrvcSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class APISubcategorias {

    @Autowired
    private SrvcSubcategoria srvcSubcategoria;

    @GetMapping("/subcategorias")
    public ResponseEntity<List<SubcategoriaDTO>> getSubcategorias(
            @RequestParam(value = "categoriaId", required = false) Long categoriaId,
            @RequestParam(value = "idioma", defaultValue = "es") String idioma) {
        List<Subcategoria> subcategorias;
        if (categoriaId == null) {
            subcategorias = srvcSubcategoria.getRepo().findAll();
        } else {
            subcategorias = srvcSubcategoria.buscarPorCategoriaId(categoriaId);
        }

        // Convertir a DTO según el idioma
        List<SubcategoriaDTO> subcategoriaDTOs = subcategorias.stream()
                .map(subcategoria -> new SubcategoriaDTO(
                        subcategoria.getId(),
                        subcategoria.getTipo_es(),
                        subcategoria.getTipo_en()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(subcategoriaDTOs);
    }
}
