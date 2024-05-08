package com.example.demo.dto;

import com.example.demo.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecDt {
    private Integer id;
    private String titre;
    private String description;
    private Date dateReclamation;
    private String nom;
    private String prenom;
    private Long apprt_number;
    private Statut statut;
    private boolean treated;
    private String commentaire;
    private String residence;


}