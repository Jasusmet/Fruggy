package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donaciones")

public class Donacion implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="donacion")
    private Double donacion;

    @Column (name ="fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;
}
