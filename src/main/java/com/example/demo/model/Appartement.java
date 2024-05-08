package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appartement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Double superficie;
    private int etage;

    @ManyToOne
    @JoinColumn(name = "residence_id")
    private Residence residence;

    @JsonIgnore
    @OneToOne(mappedBy = "appartement")
    private UserMob proprietaire;
    @JsonIgnore
    @OneToMany(mappedBy = "appartement", cascade = CascadeType.ALL)
    private List<CotisationApp> cotisations;
    @JsonIgnore
    @OneToMany(mappedBy = "appartement", cascade = CascadeType.ALL)
    private List<CotisationAF> cotisationsAF;

}
