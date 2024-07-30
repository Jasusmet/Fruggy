package com.eoi.Fruggy.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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

    @NotBlank(message = "El nombre del producto es obligatorio.")
    @Size(max = 45, message = "El nombre del producto no puede tener más de 45 caracteres.")
    @Column(name = "nombreProducto", length = 45)
    private String nombreProducto;

    @NotBlank(message = "La marca es obligatoria.")
    @Size(max = 45, message = "La marca no puede tener más de 45 caracteres.")
    @Column(name = "marca", length = 45)
    private String marca;

    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres.")
    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column (name ="activo", nullable = true)
    private Boolean activo;

    @Column(name = "pathImagen", length = 500)
    private String pathImagen;

    @JsonManagedReference
    @OneToMany
    private Set<Precio> precios = new HashSet<>();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategoria_id", foreignKey = @ForeignKey(name = "fk_producto_subcategoria"))
    private Subcategoria subcategoria;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "producto_descuento",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "descuento_id")
    )
    private Set<Descuento> descuentos = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    private Imagen imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supermercado_id", foreignKey = @ForeignKey(name = "fk_producto_supermercado"))
    private Supermercado supermercado;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ValProducto> valoraciones = new HashSet<>();


    @Transient
    private Double precioConDescuento;

    @Transient
    private Double porcentajeDescuento;
}

