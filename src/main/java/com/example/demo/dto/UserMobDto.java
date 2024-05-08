package com.example.demo.dto;

import com.example.demo.model.Appartement;
import com.example.demo.model.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMobDto {
    private int id;
    private String cin;
    private String pseudoname;
    private String nom;
    private String prenom;
    private String tel;
    private Date dateNaissance;
    private String sexe;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    private Long appartementId;
    private Long appartementNumber;
    private Long residenceId;
    private String residenceNom;

}