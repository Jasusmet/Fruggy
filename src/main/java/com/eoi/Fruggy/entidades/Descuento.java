package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column (name ="fechaInicio")
    private LocalDate fechaInicio;

    @Column (name ="fechaFin")
    private LocalDate fechaFin;

    @Column (name ="activo", nullable = false)
    private Boolean activo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "precios_id", foreignKey = @ForeignKey(name = "fk_descuentos_precio"))
    private Precio descuentosPrecios;

    @OneToOne(mappedBy = "descuento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipoDescuento_id", foreignKey = @ForeignKey(name = "fk_descuento_tipoDescuento"))
    private TipoDescuento descuentoTipoDescuento;

}
