package com.example.demo.dto;

import com.example.demo.model.CotisationApp;
import com.example.demo.model.UserMob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CotisationProprietaireDTO {
    private CotisationApp cotisationApp;
    private UserMob proprietaire;
}
