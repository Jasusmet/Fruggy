package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "cesta_productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CestaProductos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cesta_id")
    private Cesta cesta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}