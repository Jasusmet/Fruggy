package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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

    @OneToOne(mappedBy = "productoPrecios", fetch = FetchType.LAZY)
    private Producto precioProductos;

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
    private Supermercado precioSupermercado;

    @OneToMany(mappedBy = "valoracionesProductosPrecios", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ValProducto> preciosValoracionesProductos;

    @OneToMany(mappedBy = "descuentosPrecios")
    private List<Descuento> descuentos;

    // Método para obtener el valor del precio
    public String getPrecio() {
        return (valor != null) ? String.format("%.2f", valor) : "No disponible";
    }
}
