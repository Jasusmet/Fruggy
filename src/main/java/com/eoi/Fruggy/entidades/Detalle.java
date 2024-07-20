package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalles")

public class Detalle implements Serializable {

    @Id
    @Column(name = "detalles_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nombreUsuario", length = 50)
    private String nombreUsuario;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "apellido", length = 255)
    private String apellido;

    @Column(name = "pathImagen", length = 500)
    private String pathImagen;

    @Column(name = "edad")
    private Integer edad;

    // Campos de dirección
    @Column(name = "calle", length = 250)
    private String calle;
    @Column(name = "municipio", length = 250)
    private String municipio;
    @Column(name = "pais", length = 250)
    private String pais;
    @Column(name = "codigoPostal")
    private Integer codigopostal;

    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Imagen imagen;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "genero_id", foreignKey = @ForeignKey(name = "fk_detalles_genero"))
    private Genero detallesGenero;

}
