package com.example.demo.repository;

import com.example.demo.model.CotisationApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotisationAppRepository extends JpaRepository<CotisationApp, Long> {
}
