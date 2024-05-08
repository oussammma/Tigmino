package com.example.demo.dto;

import com.example.demo.model.CotisationAF;
import com.example.demo.model.UserMob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CotisationAFProprietaireDTO {
    private CotisationAF cotisationAF;
    private UserMob proprietaire;
}
