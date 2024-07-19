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
@Table(name = "listas")

public class Lista implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="nombreLista",length = 255)
    private String nombreLista;
    private LocalDateTime fechaLista;


    @OneToMany(mappedBy = "listaFavoritos", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Favorito> listaFavoritos;

}
