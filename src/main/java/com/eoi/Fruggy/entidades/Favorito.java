package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favoritos")

public class Favorito implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "precios_id")
    private Precio precio;

    @ManyToOne
    @JoinColumn(name = "listas_id")
    private Lista lista;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}
