package com.example.demo.repository;

import com.example.demo.dto.CotisationAFProprietaireDTO;
import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.model.CotisationAF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotisationAFRepository extends JpaRepository<CotisationAF, Long> {
    @Query("SELECT NEW com.example.demo.dto.CotisationAFProprietaireDTO(ca, a.proprietaire) FROM Appartement a JOIN a.cotisationsAF ca WHERE ca.appelDeFonds.id = :appelDeFondsId")
    List<CotisationAFProprietaireDTO> findByAppelDeFondsId(Long appelDeFondsId);
    @Query("SELECT COALESCE(SUM(ca.montant), 0) FROM CotisationAF ca WHERE ca.appelDeFonds.id = :appelDeFondsId")
    double getTotalMontantByAppelDeFondsId(@Param("appelDeFondsId") Long appelDeFondsId);

    @Query("SELECT NEW com.example.demo.dto.CotisationAFProprietaireDTO(ca, a.proprietaire) FROM Appartement a JOIN a.cotisationsAF ca JOIN a.residence res WHERE res.syndic.id = :syndicId AND ca.appelDeFonds.statut = 'completed'")
    List<CotisationAFProprietaireDTO> findByCompletedAppelDeFonds(Integer syndicId);


}
