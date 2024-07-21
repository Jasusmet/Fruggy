package com.eoi.Fruggy.apis;

import com.eoi.Fruggy.entidades.Categoria;
import com.eoi.Fruggy.entidades.Subcategoria;
import com.eoi.Fruggy.servicios.SrvcCategoria;
import com.eoi.Fruggy.servicios.SrvcSubcategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class APISubcategorias { // necesitamos este api para JS

    @Autowired
    private SrvcSubcategoria srvcSubcategoria;

    @GetMapping("/subcategorias")
    public ResponseEntity<List<Subcategoria>> getSubcategorias(@RequestParam("categoriaId") Long categoriaId) {
        if (categoriaId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Subcategoria> subcategorias = srvcSubcategoria.buscarPorCategoriaId(categoriaId);
        return ResponseEntity.ok(subcategorias);
    }
}
