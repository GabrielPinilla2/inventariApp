package com.erp.inventariapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.erp.inventariapp.enums.RoleUserEnum;

import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApp implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUserEnum roleUser;

    @Column(nullable = false)
    private Boolean state;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idperson", referencedColumnName = "idperson")
    private Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleUser.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // o se puede usar una lógica más personalizada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // o se puede usar `state` si representa bloqueo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // se puede manejar con una fecha si se necesita
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(state);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}

