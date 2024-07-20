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
@Table(name = "precios")

public class Precio implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="fechaInicio")
    private LocalDateTime fechaInicio;

    @Column (name ="fechaFin")
    private LocalDateTime fechaFin;

    @Column (name ="activo" , nullable = false )
    private Boolean activo;

    @Column(name = "valor")
    private Double valor;

    @OneToMany(mappedBy = "preciosCesta", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Cesta> cestaPrecios;

    @OneToMany(mappedBy = "precioFavoritos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Favorito> preciosFavoritos;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "productos_id", foreignKey = @ForeignKey(name = "fk_precios_productos"))
    private Producto precioProductos;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "supermercados_id", foreignKey = @ForeignKey(name = "fk_precios_supermercados"))
    private Supermercado precioSupermercados;

    @OneToMany(mappedBy = "valoracionesProductosPrecios", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ValProducto> preciosValoracionesProductos;

    @OneToMany(mappedBy = "descuentosPrecios", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Descuento> preciosDescuentos;

    // Método para obtener el valor del precio
    public String getPrecio() {
        return (valor != null) ? String.format("%.2f", valor) : "No disponible";
    }
}
