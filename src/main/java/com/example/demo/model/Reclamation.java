package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private String description;
    private Date dateReclamation;
    private boolean treated;
    private String commentaire;
    @Enumerated(value = EnumType.STRING)
    private Statut statut;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "preuve_id")
    private FileDB preuve;
    @ManyToOne
    @JoinColumn(name = "user_mob_id")
    private UserMob mobileUser;

    @OneToMany(mappedBy = "reclamation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commentaire> commentaires;

}
