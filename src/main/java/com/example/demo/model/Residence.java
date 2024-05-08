package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Residence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private  String codeRes;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "syndic_id")
    private WebAdmin syndic;

    @JsonIgnore
    @OneToMany(mappedBy = "residence")
    private List<Bloc> blocs;

    @JsonIgnore
    @OneToMany(mappedBy = "residence")
    private List<Appartement> appartements;


}