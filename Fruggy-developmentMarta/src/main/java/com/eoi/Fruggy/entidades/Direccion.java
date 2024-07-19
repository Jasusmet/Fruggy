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
@Table(name = "direcciones")

public class Direccion implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="calle",length = 200)
    private String calle;

    @Column (name ="municipio",length = 200)
    private String municipio;

    @Column (name ="pais",length = 200)
    private String pais;

    @Column (name ="codigopostal")
    private Integer codigopostal;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_direccion_usuario"))
    private Usuario usuarioDireccion;

}
