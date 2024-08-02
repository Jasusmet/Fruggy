package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cestas")

public class Cesta implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column (name ="fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "precio_id")
    private Precio precio;

    @OneToMany(mappedBy = "cesta")
    private Set<CestaProductos> cestaProductos;


}
