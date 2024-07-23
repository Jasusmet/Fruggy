package com.eoi.Fruggy.apis;

import com.eoi.Fruggy.entidades.Producto;
import com.eoi.Fruggy.servicios.SrvcProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class APIProductos {

        @Autowired
        private SrvcProducto productoSrvc;

        @GetMapping
        public ResponseEntity<Page<Producto>> getProductos(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {

            Page<Producto> products = productoSrvc.getRepo().findAll(PageRequest.of(page, size));
            return ResponseEntity.ok(products);
        }
    }
