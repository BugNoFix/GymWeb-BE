package com.marcominaudo.gymweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String uuid;

    @Column(unique=true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    private LocalDate subscriptionStart;

    private LocalDate subscriptionEnd;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private Boolean privacy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Un personal trainer ha piu clienti
    //@OneToMany
    //List<User> customers = new ArrayList<>();

    //Piu clienti hanno un solo pt
    @ManyToOne
    @JoinColumn(name = "pt_id")
    User pt;

    Boolean isActive = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
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
        return isActive.booleanValue();
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();
        this.privacy = false;
    }
}
