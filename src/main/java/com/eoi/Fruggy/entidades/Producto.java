package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")

public class Producto implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // hay que cambiar en todas las entidades a Long sustituyendo a  long

    @Column (name ="nombreProducto",length = 45)
    private String nombreProducto;

    @Column (name ="marca",length = 45)
    private String marca;

    @Column (name ="descripcion",length = 255)
    private String descripcion;

    @Column (name ="activo", nullable = true)
    private Boolean activo;

    @Column(name = "pathImagen", length = 500)
    private String pathImagen;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "precio_id")
    private Precio productoPrecios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoria_id", foreignKey = @ForeignKey(name = "fk_producto_subcategoria"))
    private Subcategoria subcategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    private Imagen imagen;

    public String getPrecio() {
        return (productoPrecios != null) ? productoPrecios.getPrecio() : "No disponible";
    }
}

