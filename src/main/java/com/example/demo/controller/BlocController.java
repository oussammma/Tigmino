package com.example.demo.controller;

import com.example.demo.model.Bloc;
import com.example.demo.model.Depense;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.DepenseRepository;
import com.example.demo.service.BlocService;
import com.example.demo.service.DepenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlocController {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private BlocService blocService;

    @GetMapping("/getAllBlocs")
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }
    @GetMapping("/blocsResidence/{residenceId}")
    public ResponseEntity<?> getBlocsByResidenceId(@PathVariable Long residenceId) {
        return ResponseEntity.ok(blocService.getBlocsByResidenceId(residenceId));
    }
}
