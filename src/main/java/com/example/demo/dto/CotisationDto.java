package com.example.demo.dto;

import com.example.demo.model.Appartement;
import com.example.demo.model.UserMob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CotisationDto {
    private Long id;
    private Double montant;
    private String type;
    private Date datePaiement;
private UserMobDto proprietaire;
}
