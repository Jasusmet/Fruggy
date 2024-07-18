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

@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "tipo", length = 255)
    private String tipo;

    @OneToMany(mappedBy = "subcategoriaCategoria", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Subcategoria> categoriasSubcategoria;
}
