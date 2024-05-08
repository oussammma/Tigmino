package com.example.demo.repository;

import com.example.demo.model.UserMob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserMobRepository extends JpaRepository<UserMob, Integer> {
    @Query("SELECT u FROM UserMob u JOIN FETCH u.appartement a JOIN a.residence")
    List<UserMob> findAllWithAppart();

    @Query("SELECT u FROM UserMob u WHERE u.type = 'AGENT'")
    List<UserMob> findAllAgents();

    @Query("SELECT u FROM UserMob u JOIN u.appartement a JOIN a.residence res WHERE res.syndic.id = :syndicId")
    List<UserMob> findBySyndicId(@Param("syndicId") Long syndicId);

    @Query("SELECT DISTINCT u FROM UserMob u WHERE u.id = :userId")
    UserMob findByUserId(@Param("userId") int userId);
    @Query("SELECT u FROM UserMob u JOIN u.appartement a JOIN a.residence res WHERE u.pseudoname = :pseudoname")
    Optional<UserMob> findByPseudoname(String pseudoname);

}
