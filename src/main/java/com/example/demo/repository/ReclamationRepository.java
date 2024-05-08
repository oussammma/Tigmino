package com.example.demo.repository;

import com.example.demo.dto.ReclamationDTO;
import com.example.demo.model.AppelDeFonds;
import com.example.demo.model.Reclamation;
import com.example.demo.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
    @Query("SELECT r FROM Reclamation r JOIN r.mobileUser mu JOIN mu.appartement a JOIN a.residence res ")
    List<Reclamation> findAllRec();

    @Query("SELECT r FROM Reclamation r JOIN r.mobileUser mu JOIN mu.appartement a JOIN a.residence res WHERE res.syndic.id = :syndicId")
    List<Reclamation> findBySyndicId(@Param("syndicId") Long syndicId);
    @Query("SELECT r.id, r.titre, r.description, r.dateReclamation, r.statut " +
            "FROM Reclamation r WHERE r.statut = :statut")
    List<Object[]> findByStatut(Statut statut);
    @Query("SELECT NEW com.example.demo.dto.ReclamationDTO(" +
            "r.id, r.titre, r.description, r.dateReclamation, " +
            "mu.nom, mu.prenom, a.number, r.statut, r.treated, r.commentaire) " +
            "FROM Reclamation r " +
            "JOIN r.mobileUser mu " +
            "JOIN mu.appartement a " +
            "JOIN a.residence res " +
            "WHERE res.id = :residenceId AND r.treated = false")
    List<ReclamationDTO> findByResidence(@Param("residenceId") Long residenceId);


    @Query("SELECT NEW com.example.demo.dto.ReclamationDTO(" +
            "r.id, r.titre, r.description, r.dateReclamation, " +
            "mu.nom, mu.prenom, a.number, r.statut, r.treated, r.commentaire) " +
            "FROM Reclamation r " +
            "JOIN r.mobileUser mu " +
            "JOIN mu.appartement a " +
            "JOIN a.residence res " +
            "WHERE res.id = :residenceId ")
    List<ReclamationDTO> findAllByResidence(@Param("residenceId") Long residenceId);

    default List<ReclamationDTO> findDTOByStatut(Statut statut) {
        List<Object[]> results = findByStatut(statut);
        return mapToDTOList(results);
    }

    default List<ReclamationDTO> mapToDTOList(List<Object[]> results) {
        List<ReclamationDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            ReclamationDTO dto = new ReclamationDTO();
            dto.setId((Integer) result[0]);
            dto.setTitre((String) result[1]);
            dto.setDescription((String) result[2]);
            dto.setDateReclamation((Date) result[3]);
            dto.setStatut((Statut) result[4]);
            dtos.add(dto);
        }
        return dtos;
    }
}
