package com.eoi.Fruggy.web.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioCtrl {

    @GetMapping("/")
    public String Inicio() {
        return "inicio";
    }
}

