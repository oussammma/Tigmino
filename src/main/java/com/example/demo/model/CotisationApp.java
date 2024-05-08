package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CotisationApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private Date datePaiement;

    @Enumerated(value = EnumType.STRING)
    private TypeCotisation type;

    private Integer year; // Store year for ANNUELLE
    private Integer month; // Store month for MENSUELLE
    private Integer trimestre; // Store trimester for TRIMESTRIELLE
    private Integer semestre; // Store semester for SEMESTRIELLE

    @ManyToOne
    @JoinColumn(name = "appartement_id")
    private Appartement appartement;
}
