package com.example.demo.controller;

import com.example.demo.model.Residence;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.ResidenceRepository;
import com.example.demo.service.AdminWebServiceImp;
import com.example.demo.service.ResidenceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@RestController
public class ResidenceController {
    @Autowired
    private final ResidenceService residenceService;
    @Autowired
    private final ResidenceRepository residenceRepository;
    @Autowired
    private final AdminWebServiceImp adminService;


    public ResidenceController(ResidenceService residenceService, ResidenceRepository residenceRepository, AdminWebServiceImp adminService) {
        this.residenceService = residenceService;
        this.residenceRepository = residenceRepository;
        this.adminService = adminService;
    }

    @GetMapping("/residences")
    public List<Residence> getAll() {
        return residenceService.getAll();
    }

    @PostMapping("/addResidence")
    public ResponseEntity<Residence> addResidence(@RequestBody Residence residence) {
        Residence savedResidence = residenceRepository.save(residence);
        return new ResponseEntity<>(savedResidence, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/residence/{id}")
    public ResponseEntity<?> updateResidenceSyndicId(@PathVariable Long id, @RequestParam Integer newSyndicId) {
        WebAdmin syndic = adminService.getAdminById(newSyndicId);
        Residence residence = residenceRepository.getReferenceById(id);
        try {
            residence.setSyndic(syndic);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update syndic ID: " + e.getMessage());
        }
    }

    @PutMapping("/updateRes/{id}")
    public Residence updateResidence(@PathVariable Long id, @RequestBody Residence updatedResidence) {
        return residenceService.updateResidence(id, updatedResidence);
    }

    @DeleteMapping("/deleteRes/{id}")
    public ResponseEntity<HttpStatus> deleteRes(@PathVariable("id") Long id) {
        residenceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/residences/{id}")
    public ResponseEntity<Residence> getResidenceById(@PathVariable Long id) {
        Optional<Residence> optionalResidence = residenceRepository.findById(id);
        if (optionalResidence.isPresent()) {
            return ResponseEntity.ok(optionalResidence.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
