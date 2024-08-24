package com.eoi.Fruggy.web.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorCtrl implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "/error/error"; // Nombre del archivo HTML sin la extensión
    }
}
