package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SrvcUsuario extends AbstractBusinessSrvc {

    private final RepoUsuario repoUsuario;
    private final PasswordEncoder passwordEncoder;


    // Se añaden estos métodos para agregar Bcrypt a contraseña cuando se cree nuevo usuario.
    @Autowired
    public SrvcUsuario(RepoUsuario repoUsuario, PasswordEncoder passwordEncoder) {
        super(repoUsuario);
        this.repoUsuario = repoUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> buscarEntidades() {
        return repoUsuario.findAll();
    }

    public Optional<Usuario> encuentraPorId(long id) {
        return repoUsuario.findById((int) id);
    }

    public void guardar(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        repoUsuario.save(usuario);
    }

    public void eliminarPorId(long id) {
        repoUsuario.deleteById((int) id);
    }
}
