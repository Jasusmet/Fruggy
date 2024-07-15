package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", length = 250)
    private String email;
    private Boolean active;

    @Column(name = "password", length = 250)
    private String password;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarioRol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
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
    @JoinColumn(name = "supermercado_id", foreignKey = @ForeignKey(name = "fk_supermercado_usuario"))
    private Supermercado supermercadoUsuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Rol role : roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRolNombre().toUpperCase());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }


}
