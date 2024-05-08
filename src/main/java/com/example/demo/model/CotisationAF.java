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
public class CotisationAF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "appartement_id")
    private Appartement appartement;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "appel_de_fonds_id")
    private AppelDeFonds appelDeFonds;

    private Double montant;
    private Date datePaiement;

}
