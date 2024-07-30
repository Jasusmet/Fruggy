package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.entidades.ValSupermercado;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import com.eoi.Fruggy.servicios.SrvcValSupermercado;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/supermercados")
public class ValoracionSupermercadoCtrl {

    private SrvcSupermercado supermercadoSrvc;
    private SrvcValSupermercado valSupermercadoSrvc;
    private SrvcUsuario usuarioSrvc;


    public ValoracionSupermercadoCtrl(SrvcSupermercado supermercadoSrvc, SrvcValSupermercado valSupermercadoSrvc, SrvcUsuario usuarioSrvc) {
        this.supermercadoSrvc = supermercadoSrvc;
        this.valSupermercadoSrvc = valSupermercadoSrvc;
        this.usuarioSrvc = usuarioSrvc;
    }


  @GetMapping("/{id}")
    public String valoracionSupermercado(Model model, @PathVariable("id") Long id) {
      Optional<Supermercado> supermercadoOptional = supermercadoSrvc.encuentraPorId(id);
      if (supermercadoOptional.isPresent()) {
          Supermercado supermercado = supermercadoOptional.get();
          List<ValSupermercado> valoraciones = valSupermercadoSrvc.buscarPorSupermercadoId(id);
          double notaMedia = valoraciones.stream().mapToDouble(ValSupermercado::getNota).average().orElse(0.0);
          model.addAttribute("supermercado", supermercado);
          model.addAttribute("valoraciones", valoraciones);
          model.addAttribute("notaMedia", notaMedia);
          model.addAttribute("nuevaValoracion", new ValSupermercado());
          return "/supermercados/detalles-supermercado";
      } else {
          return "redirect:/supermercados";
      }
  }

    @PostMapping("/valorar/{id}")
    public String valorarSupermercado(@PathVariable("id") Long id, @Valid @ModelAttribute("nuevaValoracion") ValSupermercado nuevaValoracion, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/supermercados/" + id;
        }
        Optional<Supermercado> supermercadoOptional = supermercadoSrvc.encuentraPorId(id);
        if (supermercadoOptional.isPresent()) {
            Supermercado supermercado = supermercadoOptional.get();
            nuevaValoracion.setSupermercado(supermercado);

            Usuario usuario = new Usuario();
            usuario.setEmail(usuario.getUsername());
            nuevaValoracion.setUsuario(usuario);

            valSupermercadoSrvc.guardar(nuevaValoracion);
        }
        return "redirect:/supermercados/" + id;
    }


}
