package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Supermercado;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.entidades.ValoracionSupermercado;
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



}
