package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subcategorias")

public class Subcategoria implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (name ="tipo", length = 255)
    private String tipo;

    @OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY)
    private Set<Producto> productos;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // Cambiado a MERGE
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_subcategoria_categoria"))
    private Categoria categoria;

    public Subcategoria(String tipo, Categoria categoria) {
        this.tipo = tipo;
        this.categoria = categoria;
    }
}
