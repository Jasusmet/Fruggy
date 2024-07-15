package com.eoi.Fruggy.web.controladores;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class APIUsuariosController {

    @Autowired
    RepoUsuario repoUsuario;

     @GetMapping("/lista-usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
         try {
             List<Usuario> usuarios = new ArrayList<>();
             repoUsuario.findAll().forEach(usuario -> usuarios.add(usuario));
             if (usuarios.isEmpty()) {
                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
             }
                return new ResponseEntity<>(usuarios, HttpStatus.OK);
         }catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
}
