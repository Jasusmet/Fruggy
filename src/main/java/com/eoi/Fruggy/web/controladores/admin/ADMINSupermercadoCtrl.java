package com.eoi.Fruggy.web.controladores.admin;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.servicios.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/supermercados")
public class ADMINSupermercadoCtrl {

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

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarSupermercados(Model model) {
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        model.addAttribute("supermercados", supermercados);
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
            return "admin/modificar-supermercado";
        } else {
            return "redirect:/admin/supermercados";
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public String actualizarSupermercado(@Valid @ModelAttribute Supermercado supermercado, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "admin/modificar-supermercado";
        }
        supermercadoSrvc.guardar(supermercado);
        return "redirect:/admin/supermercados";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarSupermercado( @PathVariable("id") Long id, Model model) {
        supermercadoSrvc.eliminarPorId(id);
        return "redirect:/admin/supermercados";
    }
}

