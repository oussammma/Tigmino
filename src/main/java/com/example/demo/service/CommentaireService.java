package com.example.demo.service;

import com.example.demo.model.Commentaire;
import com.example.demo.model.Reclamation;
import com.example.demo.model.UserMob;
import com.example.demo.repository.CommentaireRepository;
import com.example.demo.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final ReclamationRepository reclamationRepository;

    @Autowired
    public CommentaireService(CommentaireRepository commentaireRepository, ReclamationRepository reclamationRepository) {
        this.commentaireRepository = commentaireRepository;
        this.reclamationRepository = reclamationRepository;
    }


    public Commentaire addCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getCommentairesByReclamationId(Integer reclamationId) {
        return commentaireRepository.findByReclamationId(reclamationId);
    }
    public Commentaire addCommentaireToReclamation(UserMob userMob, Reclamation reclamation, String contenu) {
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenu);
        commentaire.setDateCommentaire(new Date());
        commentaire.setReclamation(reclamation);
        commentaire.setCoproprietaire(userMob);
        return commentaireRepository.save(commentaire);
    }

    public Commentaire addCommentToReclamation(UserMob userMob, Reclamation reclamation, String contenu) {
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenu);
        commentaire.setDateCommentaire(new Date());
        commentaire.setReclamation(reclamation);
        commentaire.setCoproprietaire(userMob);
        return commentaireRepository.save(commentaire);
    }
}
