package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Imagen;
import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.entidades.ValoracionSupermercado;
import com.eoi.Fruggy.servicios.SrvcImagen;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import com.eoi.Fruggy.servicios.SrvcValSupermercado;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/supermercados")
public class SupermercadoCtrl {

    private final SrvcSupermercado supermercadoSrvc;
    private final SrvcValSupermercado valSupermercadoSrvc;
    private final SrvcUsuario usuarioSrvc;
    private final SrvcImagen imagenSrvc;

    public SupermercadoCtrl(SrvcSupermercado supermercadoSrvc, SrvcValSupermercado valSupermercadoSrvc, SrvcUsuario usuarioSrvc, SrvcImagen imagenSrvc) {
        this.supermercadoSrvc = supermercadoSrvc;
        this.valSupermercadoSrvc = valSupermercadoSrvc;
        this.usuarioSrvc = usuarioSrvc;
        this.imagenSrvc = imagenSrvc;
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public String listarSupermercados(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "nombreSuper") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection,
                                      Model model) {
        Page<Supermercado> paginaSupermercados = supermercadoSrvc.obtenerSupermercadosPaginados(page, size, sortField, sortDirection);
        // Crear una lista de números de página = da fallos sin esto
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < paginaSupermercados.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        // Calcular la nota media para cada supermercado
        for (Supermercado supermercado : paginaSupermercados) {
            Double notaMedia = valSupermercadoSrvc.calcularNotaMedia(supermercado.getId());
            supermercado.setNotaMedia(notaMedia);
        }

        model.addAttribute("supermercados", paginaSupermercados);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", paginaSupermercados.getTotalPages());
        model.addAttribute("currentSortField", sortField);
        model.addAttribute("currentSortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");
        return "/supermercados/lista-supermercados";
    }

    //    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detalles/{id}")
    public String verDetallesSupermercado(@PathVariable("id") long id, Model model) throws Throwable {
        Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + id));

        List<ValoracionSupermercado> valoraciones = valSupermercadoSrvc.obtenerValoracionesPorSupermercado(id);
        Double notaMedia = valSupermercadoSrvc.calcularNotaMedia(id);

        model.addAttribute("supermercado", supermercado);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("notaMedia", notaMedia);
        model.addAttribute("valoracion", new ValoracionSupermercado());
        model.addAttribute("imagenes", supermercado.getImagenes());

        return "supermercados/detalles-supermercado";
    }

    //FUNCIONA PERO HAY QUE HACER UN LOGIN PARA VER SI ES CORRECTO.
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/detalles/{supermercadoId}/guardar")
    public String guardarValoracion(@PathVariable Long supermercadoId,
                                    @Valid @ModelAttribute("valoracion") ValoracionSupermercado valoracion,
                                    BindingResult result,
                                    Principal principal,
                                    Model model) throws Throwable {
        if (result.hasErrors()) {
            Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId)
                    .orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + supermercadoId));
            List<ValoracionSupermercado> valoraciones = valSupermercadoSrvc.obtenerValoracionesPorSupermercado(supermercadoId);
            model.addAttribute("supermercado", supermercado);
            model.addAttribute("valoraciones", valoraciones);
            return "supermercados/detalles-supermercado";
        }

        String username = principal.getName();
        Usuario usuario = usuarioSrvc.getRepo().findByEmail(username);
        valoracion.setUsuario(usuario);

        Supermercado supermercado = (Supermercado) supermercadoSrvc.encuentraPorId(supermercadoId)
                .orElseThrow(() -> new IllegalArgumentException("ID de supermercado inválido: " + supermercadoId));
        valoracion.setSupermercado(supermercado);

        valSupermercadoSrvc.guardar(valoracion);
        return "redirect:/supermercados/detalles/" + supermercadoId;
    }
}
