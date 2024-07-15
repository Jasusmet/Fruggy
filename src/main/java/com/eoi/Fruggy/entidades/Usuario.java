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
@Table(name = "usuarios")

public class Usuario implements Serializable {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name ="email",length = 50)
    private String email;

    @Column (name ="password",length = 250)
    private String password;

    @Column (name ="telefono",length = 30)
    private String telefono;

    @OneToMany(mappedBy = "usuarioRol", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Rol> roles;

    @OneToMany(mappedBy = "usuarioDireccion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Direccion> usuarioDirecciones;

    @OneToMany(mappedBy = "usuarioDonacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Donacion> usuarioDonaciones;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "detalles_id", foreignKey = @ForeignKey(name = "fk_usuario_detalles"))
    private Detalle detallesUsuario;

    @OneToOne(mappedBy = "cestaUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cesta cestaUsuarios; // No se usa Set <> con OneToOne

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "supermercado_id", foreignKey =  @ForeignKey(name = "fk_supermercado_usuario"))
    private Supermercado supermercadoUsuario;
}
