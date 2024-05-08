package com.example.demo.repository;

import com.example.demo.model.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepenseRepository extends JpaRepository<Depense, Integer> {
    @Query("SELECT DISTINCT d FROM Depense d JOIN FETCH d.blocDepenses bd JOIN FETCH bd.bloc bloc JOIN FETCH bloc.residence")
    List<Depense> findAllWithBlocsAndResidences();
    @Query("SELECT DISTINCT d FROM Depense d " +
            "JOIN d.blocDepenses bd " +
            "JOIN bd.bloc bloc " +
            "JOIN bloc.residence residence " +
            "WHERE residence.id = :residenceId")
    List<Depense> findAllByResidenceId(@Param("residenceId") Long residenceId);


}
