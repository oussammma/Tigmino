package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String service;
    private String adresse;
    private Long IdFiscal;
    private Long ICE;
    @ManyToMany(fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "logo_id",
            joinColumns = {
                    @JoinColumn(name = "fournisseur_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "logo_id")
            }
    )
    private Set<FileDB> logo;
    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Responsable> responsables = new HashSet<>();

}
