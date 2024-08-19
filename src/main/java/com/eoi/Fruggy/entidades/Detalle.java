package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalles")

public class Detalle implements Serializable {

    @Id
    @Column(name = "detalles_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "nombreUsuario", length = 50)
    private String nombreUsuario;


    @Column(name = "nombre", length = 255)
    private String nombre;


    @Column(name = "apellido", length = 255)
    private String apellido;


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

    @NotNull(message = "El género es obligatorio")
    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

}
