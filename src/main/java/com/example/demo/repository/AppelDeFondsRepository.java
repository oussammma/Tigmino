package com.example.demo.repository;

import com.example.demo.model.AppelDeFonds;
import com.example.demo.model.CotisationAF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppelDeFondsRepository extends JpaRepository<AppelDeFonds, Long> {
    @Query("SELECT a FROM AppelDeFonds a WHERE a.syndic.id = :syndicId")
    List<AppelDeFonds> findBySyndicId(@Param("syndicId") Long syndicId);
    @Query("SELECT af FROM AppelDeFonds af " +
            "INNER JOIN af.syndic w " +
            "INNER JOIN w.residences r " +
            "INNER JOIN r.appartements a " +
            "INNER JOIN a.proprietaire u " +
            "WHERE u.id = :userMobId")
    List<AppelDeFonds> findByUserMobId(@Param("userMobId") Long userMobId);
}