package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResidentWithApartmentAndResidenceDTO {
    private String residentName;
    private String apartmentNumber;
    private String residenceAddress;

}
