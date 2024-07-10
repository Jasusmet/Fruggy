package com.eoi.Fruggy.config;

import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioDetailsSrvc implements UserDetailsService {

    @Autowired
    RepoUsuario repoUsuarios;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repoUsuarios.findByNombreUsuario(username);
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role-> new SimpleGrantedAuthority(role.getRolNombre()))
                .collect(Collectors.toSet());
        //return org.springframework.security.core.userdetails.User(username, usuario.getPassword(),authorities);
        return new User(username, usuario.getPassword(),authorities);
    }
}
