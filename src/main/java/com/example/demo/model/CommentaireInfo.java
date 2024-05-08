package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
public class CommentaireInfo {
    private Integer id;
    private String contenu;
    private Date dateCommentaire;
    private Integer userMobId;
    private String nom;
    private String prenom;

    public CommentaireInfo(Integer id, String contenu, Date dateCommentaire, Integer userMobId, String nom, String prenom) {
        this.id = id;
        this.contenu = contenu;
        this.dateCommentaire = dateCommentaire;
        this.userMobId = userMobId;
        this.nom = nom;
        this.prenom = prenom;
    }}