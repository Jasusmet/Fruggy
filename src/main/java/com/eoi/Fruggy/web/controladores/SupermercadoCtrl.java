package com.eoi.Fruggy.web.controladores;


import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.servicios.SrvcSupermercado;
import com.eoi.Fruggy.servicios.SrvcValSupermercado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/supermercados")
public class SupermercadoCtrl {

    private final SrvcSupermercado supermercadoSrvc;
    private final SrvcValSupermercado valSupermercadoSrvc;

    public SupermercadoCtrl(SrvcSupermercado supermercadoSrvc, SrvcValSupermercado valSupermercadoSrvc) {
        this.supermercadoSrvc = supermercadoSrvc;
        this.valSupermercadoSrvc = valSupermercadoSrvc;
    }

    @GetMapping
    public String listarSupermercados(Model model) {
        List<Supermercado> supermercados = supermercadoSrvc.buscarEntidades();
        model.addAttribute("supermercados", supermercados);
        return "/supermercados/lista-supermercados";
    }



}
