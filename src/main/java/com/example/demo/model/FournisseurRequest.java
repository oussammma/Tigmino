package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FournisseurRequest {
    private Fournisseur fournisseur;
    private FileDB logo;
    private Set<Responsable> responsables;
}
