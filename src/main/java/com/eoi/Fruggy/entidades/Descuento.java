package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "descuentos")

public class Descuento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="descuentos_id")
    private Long id;

    @Column (name ="porcentaje")
    private Double porcentaje;

    @Column (name ="fechaInicio")
    private LocalDate fechaInicio;

    @Column (name ="fechaFin")
    private LocalDate fechaFin;

    @Column (name ="activo", nullable = false)
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "tipodescuento_id")
    private TipoDescuento tipoDescuento;

    public Descuento(double porcentaje, LocalDate now, LocalDate localDate, boolean activo, Object o, TipoDescuento descuentoPorVolumen) {
        this.porcentaje = porcentaje;
        this.fechaInicio = now;
        this.fechaFin = localDate;
        this.activo = activo;
    }
}
