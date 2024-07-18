package com.eoi.Fruggy.servicios;

import com.eoi.Fruggy.entidades.Usuario;

public interface IUsuarioSrvc {
    public String getEncodedPassword (Usuario usuario);
}