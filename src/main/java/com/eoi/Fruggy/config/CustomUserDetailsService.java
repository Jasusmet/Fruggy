package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    RepoUsuario repoUsuario;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<Usuario> usuario = repoUsuario.findByNombreUsuario(username);
//        if (usuario.isPresent()) {
//            return usuario.get();
//        }
//        else
//        {
//            throw new UsernameNotFoundException("Usuario no encontrado");
//        }
//    }
//}
