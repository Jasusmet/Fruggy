package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Detalle;
import com.eoi.Fruggy.entidades.Genero;
import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.servicios.SrvcDetalle;
import com.eoi.Fruggy.servicios.SrvcGenero;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcRol;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class DetalleCtrl {

    @Autowired
    private SrvcDetalle detallesSrvc;
    @Autowired
    private SrvcGenero generoSrvc;
    @Autowired
    private SrvcImagen imagenSrvc;
    @Autowired
    private SrvcRol rolSrvc;

    @GetMapping("/detalles/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalle = detallesSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            List<Genero> generos = generoSrvc.buscarEntidades();
            List<Rol> roles = rolSrvc.buscarEntidades();
            model.addAttribute("detalle", detalle.get());
            model.addAttribute("generos", generos);
            model.addAttribute("roles", roles);
            return "detalles";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error"; // Editar `error.html`
        }
    }

    @PostMapping("/detalles/{id}")
    public String guardarDetalle(@PathVariable("id") Long id,
                                 @ModelAttribute Detalle detalleActualizado,
                                 @RequestParam("rol") String rol,
                                 @RequestParam("file") MultipartFile file,
                                 Model model) {
        Optional<Detalle> detalleOptional = detallesSrvc.encuentraPorId(id);
        if (detalleOptional.isPresent()) {
            Detalle existente = detalleOptional.get();

            existente.setNombreUsuario(detalleActualizado.getNombreUsuario());
            existente.setNombre(detalleActualizado.getNombre());
            existente.setApellido(detalleActualizado.getApellido());
            existente.setEdad(detalleActualizado.getEdad());
            existente.setDetallesGenero(detalleActualizado.getDetallesGenero());

            Set<Rol> roles = new HashSet<>();
            if (rol.equals("ROLE_USER")) {
                roles.add(rolSrvc.getRepo().findByRolNombre("ROLE_USER"));
            } else if (rol.equals("ROLE_ADMIN")) {
                roles.add(rolSrvc.getRepo().findByRolNombre("ROLE_ADMIN"));
            }
            existente.setId(id);

            if (!file.isEmpty()) {
                try {
                    // Guardar la imagen en el servidor
                    String directoryPath = "D:\\img";
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs(); // Crear directorios si no existen
                    }
                    String fileName = file.getOriginalFilename();
                    File targetFile = new File(directoryPath + File.separator + fileName);
                    file.transferTo(targetFile);

                    // Asignar la ruta de la imagen a la entidad
                    existente.setPathImagen(directoryPath + "/" + fileName);
                } catch (IOException e) {
                    model.addAttribute("error", "Error al guardar la imagen: " + e.getMessage());
                    return "error";
                }
            }

            try {
                detallesSrvc.guardar(existente);
                model.addAttribute("detalle", existente);
                model.addAttribute("mensaje", "Detalle actualizado correctamente");
                return "redirect:/detalles-actualizados/" + existente.getId();
            } catch (Exception e) {
                model.addAttribute("error", "Error al guardar el detalle: " + e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }

    @GetMapping("/detalles-actualizados/{id}")
    public String verDetallesActualizados(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalleActualizado = detallesSrvc.encuentraPorId(id);
        if (detalleActualizado.isPresent()) {
            Detalle detalle = detalleActualizado.get();
            System.out.println("Detalle encontrado: " + detalle.getNombreUsuario()); // depuración
            model.addAttribute("detalle", detalle);
            return "detalles-actualizados";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }
}
