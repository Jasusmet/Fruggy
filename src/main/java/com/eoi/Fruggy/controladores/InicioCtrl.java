package com.eoi.Fruggy.controladores;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class InicioCtrl {

    @GetMapping("/")
    public String Inicio() {
        return "inicio";
    }

}
