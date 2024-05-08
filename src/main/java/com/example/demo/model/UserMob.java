package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMob implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cin;
    private String passkey;
    private String fonction;
    private String lastActionUser;
    private char status;
    private char blockAccess;
    private int numberOfTries;
    private int numberOfTriesAllowed;
    private String nom;
    private String prenom;
    private String tel;
    private Date dateNaissance;
    private String pseudoname;
    private Date lastActionDate;
    private String sexe;
    private String service;
    private String mail;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @JsonIgnore
    @OneToMany(mappedBy = "mobileUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reclamation> reclamations;

@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appartement_id")
    private Appartement appartement;

    @OneToMany(mappedBy = "mobileUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Tache> taches;

    @JsonIgnore
    @OneToMany(mappedBy = "coproprietaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commentaire> commentaires;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
