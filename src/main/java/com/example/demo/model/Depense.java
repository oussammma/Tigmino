package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String objectif;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private ModePaiement mode;
    private String preuvePaiementPath;
    private Double montantTotal;
    private Date datePaiement;
    private Date dateDebut;
    private Date dateFin;
    @Enumerated(value = EnumType.STRING)
    private TypeExp typeDepense;
    private String commentaire;


    @JsonIgnoreProperties("depense")
    @OneToMany(mappedBy = "depense")
    private List<BlocDepense> blocDepenses;
}
