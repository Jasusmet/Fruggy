package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cestas")

public class Cesta implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    @Column(name = "nombre", length = 100)

    private String nombre;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "precio_id")
    private Precio precio;

    @OneToMany(mappedBy = "cesta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProductoEnCesta> productosEnCesta = new HashSet<>();

    @Column(name = "es_principal")
    private Boolean esPrincipal = false;

    // Método para agregar productos
    public void addProducto(Producto producto, Integer cantidad, String comentario) {
        ProductoEnCesta productoEnCesta = productosEnCesta.stream()
                .filter(p -> p.getProducto().equals(producto))
                .findFirst()
                .orElse(null);

        if (productoEnCesta != null) {
            productoEnCesta.setCantidad(productoEnCesta.getCantidad() + cantidad);
            productoEnCesta.setComentario(comentario);
        } else {
            productoEnCesta = new ProductoEnCesta();
            productoEnCesta.setProducto(producto);
            productoEnCesta.setCantidad(cantidad);
            productoEnCesta.setComentario(comentario);
            productoEnCesta.setCesta(this);
            productosEnCesta.add(productoEnCesta);
        }
    }

    public Set<Producto> getProductos() {
        return productosEnCesta.stream()
                .map(ProductoEnCesta::getProducto)
                .collect(Collectors.toSet());
    }
    @Transient // No se almacena en la base de datos
    public Double getTotal() {
        return productosEnCesta.stream()
                .mapToDouble(p -> p.getProducto().getPrecios().stream().findFirst()
                        .map(precio -> precio.getValor() * p.getCantidad())
                        .orElse(0.0))
                .sum();
    }


}
