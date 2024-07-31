package com.eoi.Fruggy.entidades;

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

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "pathImagen", length = 500)
    private String pathImagen;

    @JsonManagedReference
    @OneToMany
    private Set<Precio> precios = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "subcategoria_id")
    private Subcategoria subcategoria;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    private Imagen imagen;

    @OneToMany(mappedBy = "producto")
    private Set<CestaProductos> cestaProductos;

}

