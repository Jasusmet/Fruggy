package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
    private Long id;

    @Column(name = "email", length = 250)
    private String email;
    private Boolean active;

    @Column(name = "password", length = 250)
    private String password;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuarioDireccion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Direccion> usuarioDirecciones;

    @OneToMany(mappedBy = "usuarioDonacion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Donacion> usuarioDonaciones;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Detalle detalle;

    @OneToOne(mappedBy = "cestaUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cesta cestaUsuarios; // No se usa Set <> con OneToOne

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "supermercado_id", foreignKey = @ForeignKey(name = "fk_supermercado_usuario"))
    private Supermercado supermercadoUsuario;
    // Relación con Imagen
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Imagen> imagenes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Rol rol : roles) {
            authorities.add(new SimpleGrantedAuthority(rol.getRolNombre()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
