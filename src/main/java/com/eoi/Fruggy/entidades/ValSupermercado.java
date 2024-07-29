package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
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
@Table(name = "valoracionesSupermercados")

public class ValSupermercado implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "comentario", length = 255)
    private String comentario;

    @Column(name = "nota")
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado supermercado;

}
