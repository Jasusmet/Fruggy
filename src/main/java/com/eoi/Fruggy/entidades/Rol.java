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
@Table(name = "roles")

public class Rol implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "rol_nombre", unique = true, nullable = false)
    private String rolNombre;

    @Column(name = "es_desc", unique = true, nullable = false)
    private String esDesc;

    @Column(name = "en_desc", unique = true, nullable = false)
    private String enDesc;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();

    public Rol(String rolNombre , String es , String en) {
        this.rolNombre = rolNombre;
        this.esDesc = es;
        this.enDesc = en;
    }
}
