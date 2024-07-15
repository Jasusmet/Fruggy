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

    @Column (name ="imagenPath",length = 255)
    private String imagenPath;

    @Column (name ="horario",length = 255)
    private String horario;

    @Column (name ="direccion", length = 255)
    private String direccion;

    @OneToOne(mappedBy = "precioSupermercados", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Precio supermercadoPrecios;

    @OneToMany(mappedBy = "valoracionSupermercadoSupermercado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ValSupermercado> supermercadosValoracionesSupermercados;

    @OneToMany(mappedBy = "supermercadoUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Usuario> supermercadosUsuarios;

}
