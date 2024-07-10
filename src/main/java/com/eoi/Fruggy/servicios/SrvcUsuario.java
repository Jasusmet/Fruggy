package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface SrvcUsuario {

    List<Usuario> listaUsuarios();

    Optional<Usuario> porIdUsuario(int id);

    void guardarUsuario(Usuario usuario);

    void eliminarUsuario(int id);
}
