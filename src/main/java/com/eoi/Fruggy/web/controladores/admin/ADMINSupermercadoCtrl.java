package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/supermercados")
public class ADMINSupermercadoCtrl {

    private static final Logger log = LoggerFactory.getLogger(ADMINSupermercadoCtrl.class);

    private final SrvcSupermercado supermercadoSrvc;
    private final SrvcUsuario usuarioSrvc;
    private final SrvcImagen imagenSrvc;
    private final SrvcPrecio precioSrvc;
    private final SrvcValSupermercado valSupermercadoSrvc;

    public ADMINSupermercadoCtrl(SrvcSupermercado supermercadoSrvc, SrvcUsuario usuarioSrvc, SrvcImagen imagenSrvc, SrvcPrecio precioSrvc, SrvcValSupermercado valSupermercadoSrvc) {
        this.supermercadoSrvc = supermercadoSrvc;
        this.usuarioSrvc = usuarioSrvc;
        this.imagenSrvc = imagenSrvc;
        this.precioSrvc = precioSrvc;
        this.valSupermercadoSrvc = valSupermercadoSrvc;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarSupermercados(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "nombreSuper") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection,
                                      Model model) {
        Page<Supermercado> paginaSupermercados = supermercadoSrvc.obtenerSupermercadosPaginados(page, size, sortField, sortDirection);
        model.addAttribute("supermercados", paginaSupermercados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaSupermercados.getTotalPages());
        model.addAttribute("currentSortField", sortField);
        model.addAttribute("currentSortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        // para comprobar si carga páginas
        log.info("Total de supermercados: {}", paginaSupermercados.getTotalElements());
        log.info("Número total de páginas: {}", paginaSupermercados.getTotalPages());
        log.info("Página actual: {}", page);

        return "admin/CRUD-Supermercados";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/agregar")
    public String agregarSupermercado(Model model) {
        model.addAttribute("supermercado", new Supermercado());
        return "admin/crear-supermercado";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardarSupermercado(@Valid @ModelAttribute Supermercado supermercado, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "admin/crear-supermercado";
        }
        supermercadoSrvc.guardar(supermercado);

        // Procesa las imágenes
        if (supermercado.getImagenesArchivo() != null) {
            for (MultipartFile file : supermercado.getImagenesArchivo()) {
                if (!file.isEmpty()) {
                    Imagen imagen = imagenSrvc.guardarImagen(file, supermercado); // Aquí se llama al servicio de imagen
                    imagen.setSupermercado(supermercado); // Establece la relación
                    imagenSrvc.guardar(imagen); // Guarda la imagen en la base de datos
                }
            }
        }

        return "redirect:/admin/supermercados";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarSupermercado(@PathVariable("id") Long id, Model model) {
        Optional<Supermercado> supermercadoOptional = supermercadoSrvc.encuentraPorId(id);
        if (supermercadoOptional.isPresent()) {
            Supermercado supermercado = supermercadoOptional.get();
            model.addAttribute("supermercado", supermercado);
            model.addAttribute("usuarios", usuarioSrvc.buscarEntidades());
            model.addAttribute("imagenes", imagenSrvc.buscarEntidades());
            model.addAttribute("precios", precioSrvc.buscarEntidades());
            model.addAttribute("valoraciones", valSupermercadoSrvc.buscarEntidades());
            model.addAttribute("imagenes", imagenSrvc.buscarEntidades());
            return "admin/modificar-supermercado";
        } else {
            return "redirect:/admin/supermercados";
        }
    }

    //IMAGENES
    @PostMapping("/{id}/agregar-imagen")
    public String agregarImagen(@PathVariable("id") Long id, @RequestParam("imagen") MultipartFile file, Model model) {
        try {
            Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(id)
                    .orElseThrow(() -> new RuntimeException("Supermercado no encontrado"));

            if (!file.isEmpty()) {
                Imagen imagen = imagenSrvc.guardarImagen(file, supermercado);
                imagen.setSupermercado(supermercado);
                imagenSrvc.guardar(imagen);
            } else {
                model.addAttribute("error", "El archivo no puede estar vacío.");
            }
        } catch (Throwable e) {
            model.addAttribute("error", "Error al agregar la imagen: " + e.getMessage());
        }
        return "redirect:/admin/supermercados/editar/" + id;
    }


    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public String actualizarSupermercado(@Valid @ModelAttribute Supermercado supermercado,
                                         BindingResult bindingResult,
                                         @RequestParam("imagenesArchivo") MultipartFile imagenesArchivo,
                                         Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "admin/modificar-supermercado";
        }

        // Guarda el supermercado
        supermercadoSrvc.guardar(supermercado);

        // Procesa la nueva imagen subida
        if (imagenesArchivo != null && !imagenesArchivo.isEmpty()) {
            // Eliminar la imagen existente si la hay
            Set<Imagen> imagenesExistentes = imagenSrvc.buscarPorSupermercado(supermercado);
            for (Imagen imagen : imagenesExistentes) {
                imagenSrvc.eliminarPorId(imagen.getId());
            }

            // Guardar la nueva imagen
            Imagen nuevaImagen = imagenSrvc.guardarImagen(imagenesArchivo, supermercado);
            nuevaImagen.setSupermercado(supermercado);
            imagenSrvc.guardar(nuevaImagen);
        }

        return "redirect:/admin/supermercados";
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarSupermercado(@PathVariable("id") Long id, Model model) {
        supermercadoSrvc.eliminarPorId(id);
        return "redirect:/admin/supermercados";
    }
}

