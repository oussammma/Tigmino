package com.example.demo.repository;

import com.example.demo.model.WebAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface WebAdminRepository extends JpaRepository<WebAdmin, Integer> {

    Optional<WebAdmin> findByPseudoname(String pseudoname);
    List<WebAdmin> findAll();

    boolean existsByContact(String contact);

    Optional<WebAdmin> findByContact(String contact);
    Optional<WebAdmin> findByEmail(String email);


    @Query("SELECT a FROM WebAdmin a WHERE a.role = 'SYNDIC'")
    List<WebAdmin> getAllSyndics();
}
