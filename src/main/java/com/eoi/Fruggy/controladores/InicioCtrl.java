package com.eoi.Fruggy.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioCtrl {

    @GetMapping("/")
    public String Inicio() {
        return "layout/index";
    }
}

