package com.eoi.Fruggy.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Email
    @NotBlank
    @Size(min = 5, max = 250)
    @Column(name = "email", length = 250)
    private String email;

    private Boolean active;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&*-]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula y un signo especial (!@#$&*-).")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_id", referencedColumnName = "detalles_id")
    private Detalle detalle;

    @OneToOne(mappedBy = "cestaUsuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cesta cestaUsuarios; // No se usa Set <> con OneToOne

    @ManyToOne
    @JoinColumn(name = "supermercado_id")
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
