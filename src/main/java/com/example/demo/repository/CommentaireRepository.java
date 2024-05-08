package com.example.demo.repository;

import com.example.demo.model.Commentaire;
import com.example.demo.model.CommentaireInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
    List<Commentaire> findByReclamationId(Integer reclamationId);
    @Query("SELECT new com.example.demo.model.CommentaireInfo(c.id, c.contenu, c.dateCommentaire, u.id, u.nom, u.prenom) " +
            "FROM Commentaire c " +
            "JOIN c.coproprietaire u " +
            "WHERE c.reclamation.id = :reclamationId")
    List<CommentaireInfo> findCommentairesInfoByReclamationId(Integer reclamationId);

}
