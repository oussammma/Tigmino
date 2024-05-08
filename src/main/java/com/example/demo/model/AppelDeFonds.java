package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppelDeFonds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectif;
    private String description;
    private double montantApp;
    private Date dateDebut;
    private Date dateLimite;
    private Date dateSupp;
    private Date datePaiement;

    @Enumerated(value = EnumType.STRING)
    private TypeExp statut;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "preuve_paiement_id")
    private FileDB preuvePaiement;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "preuve_appel_id")
    private FileDB preuveAppel;

    private Double montant;
    private String commentaire;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "syndic_id")
    private WebAdmin syndic;

    @JsonIgnore
    @OneToMany(mappedBy = "appelDeFonds", cascade = CascadeType.ALL)
    private List<CotisationAF> cotisationsAF;
}
