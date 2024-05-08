package com.example.demo.repository;

import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.model.Appartement;
import com.example.demo.model.CotisationApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppartementRepository extends JpaRepository<Appartement, Long> {
    List<Appartement> findByResidenceId(Long residenceId);
    List<Appartement> findByResidence_Syndic_Id(Long syndicId);

    @Query("SELECT a.cotisations FROM Appartement a WHERE a.proprietaire.id = :proprietaireId")
    List<List<CotisationApp>> findCotisation(Integer proprietaireId);
    @Query("SELECT NEW com.example.demo.dto.CotisationProprietaireDTO(ca, a.proprietaire) FROM Appartement a JOIN a.cotisations ca JOIN a.residence res WHERE res.syndic.id = :syndicId")
    List<CotisationProprietaireDTO> findCotisationSyndic(Integer syndicId);

    @Query("SELECT ca FROM CotisationApp ca " +
            "INNER JOIN Appartement a ON ca.appartement.id = a.id " +
            "INNER JOIN UserMob u ON a.id = u.appartement.id " +
            "WHERE u.id = :proprietaireId")
    List<CotisationApp> findByProprietaireId(@Param("proprietaireId") Long proprietaireId);


}