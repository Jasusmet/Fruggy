package com.eoi.Fruggy.servicios;


import com.eoi.Fruggy.entidades.Rol;
import com.eoi.Fruggy.entidades.Usuario;
import com.eoi.Fruggy.repositorios.RepoDetalle;
import com.eoi.Fruggy.repositorios.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioSecurityImpl implements IUsuarioSrvc, UserDetailsService {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoDetalle repoDetalle; // ¿Cómo implementariamos los detalles de cada usuario?

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String getEncodedPassword (Usuario usuario) {
        String password = usuario.getPassword();
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repoUsuario.findByEmail(username);
        if (usuario==null){
            throw new UsernameNotFoundException("Usuario no encontrado"+ username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Rol rol: usuario.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(rol.getRolNombre()));
        }
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), grantedAuthorities);
    }
}

