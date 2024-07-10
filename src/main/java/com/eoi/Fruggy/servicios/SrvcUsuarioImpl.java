package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SrvcUsuarioImpl implements SrvcUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    @Override
    public List<Usuario> listaUsuarios() {
        return repoUsuario.findAll();
    }

    @Override
    public Optional<Usuario> porIdUsuario(int id) {
        return repoUsuario.findById(id);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        repoUsuario.save(usuario);
    }

    @Override
    public void eliminarUsuario(int id) {
        repoUsuario.deleteById(id);
    }
}
