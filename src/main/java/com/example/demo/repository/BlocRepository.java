package com.example.demo.repository;

import com.example.demo.model.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlocRepository  extends JpaRepository<Bloc, Long> {
    List<Bloc> findByResidenceId(Long residenceId);
}
