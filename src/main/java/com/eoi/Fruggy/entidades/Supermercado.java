package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supermercados")

public class Supermercado implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="nombreSuper",length = 255)
    private String nombreSuper;

    @Column (name ="url",length = 255)
    private String url;

    @Column(name = "imagen_path", length = 500)
    private String imagenPath;

    @Column (name ="horario",length = 255)
    private String horario;

    // Campos de dirección
    @Column(name = "calle", length = 250)
    private String calle;
    @Column(name = "municipio", length = 250)
    private String municipio;
    @Column(name = "pais", length = 250)
    private String pais;
    @Column(name = "codigoPostal")
    private Integer codigopostal;

    @OneToMany(mappedBy = "precioSupermercado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Precio> supermercadoPrecios;

    @OneToMany(mappedBy = "valoracionSupermercadoSupermercado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ValSupermercado> supermercadosValoracionesSupermercados;

    @OneToMany(mappedBy = "supermercadoUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Usuario> supermercadosUsuarios;

    // Relación con Imagen
    @OneToMany(mappedBy = "supermercado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Imagen> imagenes;

}
