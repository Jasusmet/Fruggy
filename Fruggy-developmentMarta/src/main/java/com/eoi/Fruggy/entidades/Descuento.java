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
@Table(name = "descuentos")

public class Descuento implements Serializable {

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "precios_id", foreignKey = @ForeignKey(name = "fk_descuentos_precio"))
    private Precio descuentosPrecios;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_de_descuentos_id", foreignKey = @ForeignKey(name = "fk_descuentos_tipoDeDescuentos"))
    private TipoDescuento descuentosTipoDeDescuentos;
}
