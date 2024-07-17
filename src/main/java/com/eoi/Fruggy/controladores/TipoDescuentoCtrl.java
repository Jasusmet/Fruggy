package com.eoi.Fruggy.controladores;

import com.eoi.Fruggy.entidades.TipoDescuento;
import com.eoi.Fruggy.servicios.SrvcTipodescuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TipoDescuentoCtrl {

    @Autowired
    private SrvcTipodescuento tipodescuentosSrvc;

    @GetMapping("/tipodescuentos")
    public String listarTipoDescuentos(Model model) {
        List<TipoDescuento> listaTipoDescuentos = tipodescuentosSrvc.buscarEntidades();
        model.addAttribute("tipodescuentos", listaTipoDescuentos);
        return "tipodescuentos";
    }
}
