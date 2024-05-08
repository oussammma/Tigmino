package com.example.demo.dto;

import com.example.demo.model.UserMob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppartementDto {
    private Long id;
    private Long number;
    private Double superficie;
    private UserMob proprietaire;
    private int etage;
}
