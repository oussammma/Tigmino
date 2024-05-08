package com.example.demo.controller;

import com.example.demo.dto.RecDt;
import com.example.demo.dto.ReclamationDTO;
import com.example.demo.model.Reclamation;
import com.example.demo.model.Statut;
import com.example.demo.repository.ReclamationRepository;
import com.example.demo.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController

public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;
    @Autowired
    private ReclamationRepository reclamationRepository;

    @GetMapping("/getAllReclamations")
    public ResponseEntity<List<ReclamationDTO>> getAllReclamations() {
        List<ReclamationDTO> reclamations = reclamationService.getAllRec();
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/getAllRecs")
    public ResponseEntity<List<RecDt>> getAllRecs() {
        List<RecDt> reclamations = reclamationService.getAllRecs();
        return ResponseEntity.ok(reclamations);
    }
    @GetMapping("/reclamation/{id}")
    public ResponseEntity<ReclamationDTO> getReclamationById(@PathVariable Integer id) {
        ReclamationDTO reclamationDTO = reclamationService.getReclamationById(id);
        if (reclamationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reclamationDTO);
    }
    @GetMapping("/reclamations/{statut}")
    public List<Object[]> getReclamationsByStatut(@PathVariable Statut statut) {
        return reclamationService.getReclamationsByStatut(statut);
    }
    @GetMapping("/reclamations")
    public List<ReclamationDTO> getAllReclamationsWithUserNames(@RequestParam Long syndicId) {
        return reclamationService.getAllReclamationsWithUserNames(syndicId);
    }
    @PutMapping("/{reclamationId}/treated")
    public ResponseEntity<ReclamationDTO> markReclamationCompleted(@PathVariable Integer reclamationId, @RequestBody Map<String, String> requestBody) {
        String comment = requestBody.get("comment");
        ReclamationDTO reclamationDTO = reclamationService.markReclamationCompleted(reclamationId, comment);
        return ResponseEntity.ok(reclamationDTO);
    }

    @GetMapping("/reclamationsRes/{residenceId}")
    public List<ReclamationDTO> findByResidence(@PathVariable("residenceId") Long residenceId) {
        return reclamationRepository.findByResidence(residenceId);
    }
    @GetMapping("/AllreclamationsRes/{residenceId}")
    public List<ReclamationDTO> findAllByResidence(@PathVariable("residenceId") Long residenceId) {
        return reclamationRepository.findAllByResidence(residenceId);
    }


}