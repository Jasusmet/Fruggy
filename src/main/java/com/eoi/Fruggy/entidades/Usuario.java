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
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))

public class Usuario implements Serializable, UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_id", nullable = false)
    private Detalle detalle;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    // Relación con Imagen
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Imagen> imagenes;

    @OneToMany(mappedBy = "usuario")
    private Set<Donacion> donaciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cesta> cestas;

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

    public Cesta getCesta() {
        return cestas.stream()
                .filter(Cesta::getEsPrincipal) // Filtra la cesta que es principal
                .findFirst()
                .orElse(null);
    }

    public Set<Cesta> getCestas() { // todas las cestas
        return cestas;
    }

    public Cesta findCestaById(Long cestaId) {
        return cestas.stream()
                .filter(c -> c.getId().equals(cestaId))
                .findFirst()
                .orElse(null);
    }

    public void addCesta(Cesta cesta) {
        if (cestas.size() < 10) {
            cesta.setUsuario(this);
            cestas.add(cesta);
        } else {
            throw new IllegalStateException("El usuario ya tiene el máximo de 10 cestas.");
        }
    }

}
