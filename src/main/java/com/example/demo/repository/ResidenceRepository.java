package com.example.demo.repository;

import com.example.demo.model.Residence;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {

    @Query("SELECT DISTINCT r FROM Residence r LEFT JOIN FETCH r.syndic LEFT JOIN FETCH r.appartements a LEFT JOIN FETCH a.proprietaire")
    List<Residence> findAll();
    @Modifying
    @Transactional
    @Query("UPDATE Residence r SET r.syndic.id = :newSyndicId WHERE r.id = :residenceId")
    void updateSyndicId(@Param("residenceId") Long residenceId, @Param("newSyndicId") Long newSyndicId);

}
