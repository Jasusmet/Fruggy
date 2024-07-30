package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.*;
import com.eoi.Fruggy.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("admin/usuarios")
public class ADMINDetalleCtrl {

    private final SrvcDetalle detalleSrvc;
    private final SrvcGenero generoSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcRol rolSrvc;
    private final SrvcUsuario usuarioSrvc;

    public ADMINDetalleCtrl(SrvcDetalle detalleSrvc, SrvcGenero generoSrvc, SrvcImagen imagenSrvc, SrvcRol rolSrvc, SrvcUsuario usuarioSrvc) {
        this.detalleSrvc = detalleSrvc;
        this.generoSrvc = generoSrvc;
        this.imagenSrvc = imagenSrvc;
        this.rolSrvc = rolSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }


    @GetMapping("/detalles/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        Optional<Detalle> detalle = detalleSrvc.encuentraPorId(id);
        if (detalle.isPresent()) {
            model.addAttribute("detalle", detalle.get());
            model.addAttribute("generos", generoSrvc.buscarEntidades());
            return "admin/detalles-usuario";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }


    @PostMapping("/detalles/{id}")
    public String guardarDetalle(@PathVariable("id") Long id,
                                 @ModelAttribute Detalle detalleActualizado,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestParam("generoId") Long generoId,
                                 Model model) throws Exception {
        Optional<Detalle> detalleOptional = detalleSrvc.encuentraPorId(id);
        if (detalleOptional.isPresent()) {
            Detalle existente = detalleOptional.get();
            // Establecer los valores actualizados
            existente.setNombreUsuario(detalleActualizado.getNombreUsuario());
            existente.setNombre(detalleActualizado.getNombre());
            existente.setApellido(detalleActualizado.getApellido());
            existente.setEdad(detalleActualizado.getEdad());
            existente.setDetallesGenero(detalleActualizado.getDetallesGenero());

            // Establecer el género
            Optional<Genero> genero = generoSrvc.getRepo().findById(generoId);
            if (genero.isPresent()) {
                existente.setDetallesGenero(genero.get());
            } else {
                model.addAttribute("error", "Género no encontrado");
                return "error";
            }

            // Campos de dirección
            existente.setCalle(detalleActualizado.getCalle());
            existente.setMunicipio(detalleActualizado.getMunicipio());
            existente.setPais(detalleActualizado.getPais());
            existente.setCodigopostal(detalleActualizado.getCodigopostal());

            // Manejo de la imagen
            if (file != null && !file.isEmpty()) {
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

                    // Crear o actualizar la entidad Imagen
                    Imagen imagen = existente.getImagen();
                    if (imagen == null) {
                        imagen = new Imagen();
                        imagen.setDetalle(existente);
                    }
                    imagen.setNombreArchivo(fileName);
                    imagen.setRuta(directoryPath);
                    imagen.setPathImagen("/images/" + fileName); // URL relativa para acceder a la imagen

                    // Guardar la imagen y establecer la relación
                    imagenSrvc.guardar(imagen);
                    existente.setImagen(imagen);

                } catch (IOException e) {
                    model.addAttribute("error", "Error al guardar la imagen: " + e.getMessage());
                    return "error";
                }
            }
            // Guardar el detalle
            try {
                detalleSrvc.guardar(existente);
                model.addAttribute("detalle", existente);
                model.addAttribute("mensaje", "Detalle actualizado correctamente");
                return "redirect:/admin/usuarios/detalles-actualizados/" + existente.getId();
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
        Optional<Detalle> detalleActualizado = detalleSrvc.encuentraPorId(id);
        if (detalleActualizado.isPresent()) {
            model.addAttribute("detalle", detalleActualizado.get());
            return "admin/detalles-usuario-actualizados";
        } else {
            model.addAttribute("error", "Detalle no encontrado");
            return "error";
        }
    }
}