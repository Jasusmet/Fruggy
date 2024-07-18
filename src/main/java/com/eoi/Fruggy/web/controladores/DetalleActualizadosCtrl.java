package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcImagen;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Controller
public class DetalleActualizadosCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;

    @GetMapping("/detalles/actualizados/{id}")
    public String detallesActualizados(@PathVariable("id") Long id, Model model) {

        Optional<Detalle> detalleActualizado = detallesSrvc.encuentraPorId(id);

        if (detalleActualizado.isPresent()) {
            model.addAttribute("detalle", detalleActualizado.get());
            return "detalles-actualizados";
        } else {
            return "error";
        }
    }
}
