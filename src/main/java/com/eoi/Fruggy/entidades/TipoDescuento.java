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
@Table(name = "tipoDeDescuentos")

public class TipoDescuento implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="fechaInicio")
    private LocalDateTime fechaInicio;

    @Column (name ="fechaFin")
    private LocalDateTime fechaFin;

    @Column (name ="activo", nullable = false)
    private Boolean activo;

    @OneToOne(mappedBy = "descuentosTipoDeDescuentos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Descuento tipoDeDescuentoDescuento;
}
