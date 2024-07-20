package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "generos")

public class Genero implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "detallesGenero", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Detalle> detallesGenero = new HashSet<>();

    public Genero(String descripcion) {
        this.descripcion = descripcion;
    }
}
