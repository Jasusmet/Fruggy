package com.eoi.Fruggy.apis;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.servicios.SrvcUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")
public class APIUsuario {

    @Autowired
    private SrvcUsuario usuarioSrvc;

    @PostMapping("/crear")
    public Usuario crearUsuario(@RequestParam String email, @RequestParam String password, @RequestParam String telefono, @RequestParam Set<String> roles) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTelefono(telefono);
        usuario.setActive(true);
        usuarioSrvc.guardar(usuario, roles);
        return usuario;
    }
}
