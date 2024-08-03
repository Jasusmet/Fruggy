package com.eoi.Fruggy.web.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InicioCtrl {

    @GetMapping("/")
    public String Inicio() {
        return "inicio";
    }
}

