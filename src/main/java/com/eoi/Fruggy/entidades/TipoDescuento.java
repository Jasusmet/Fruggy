package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipoDescuentos")

public class TipoDescuento implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column (name ="porcentaje")
    private Double porcentaje;

    @Column (name ="fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column (name ="fechaFin", nullable = false)
    private LocalDate fechaFin;

    @Column (name ="activo", nullable = false)
    private Boolean activo;

    @ManyToMany(mappedBy = "tipoDescuentos")
    private Set<Producto> productos = new HashSet<>();

    @OneToMany(mappedBy = "descuentoTipoDescuento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Descuento> descuentoTipoDescuento = new HashSet<>();


    public TipoDescuento(String descuentoPorVolumen, boolean activo, LocalDate localDate, LocalDate localDate1, double porcentaje) {
        this.tipo = descuentoPorVolumen;
        this.activo = activo;
        this.fechaInicio = localDate;
        this.fechaFin = localDate1;
        this.porcentaje = porcentaje;
    }
}
