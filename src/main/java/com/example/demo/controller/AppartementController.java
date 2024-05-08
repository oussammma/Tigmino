package com.example.demo.controller;

import com.example.demo.dto.CotisationDto;
import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.model.Appartement;
import com.example.demo.model.CotisationApp;
import com.example.demo.service.appartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppartementController {

    private final appartementService service;

    public AppartementController(appartementService service) {
        this.service = service;
    }

    @GetMapping("/getAllApps")
    public ResponseEntity<List<Appartement>> getAllTasks() {
        List<Appartement> apps = service.getAllApps();
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }
    @GetMapping("/cotisation/{proprietaireId}")
    public ResponseEntity<List<CotisationApp>> getCotisationsByProprietaireId(@PathVariable Long proprietaireId) {
        List<CotisationApp> cotisations = service.getCotisationsByProprietaireId(proprietaireId);
        return ResponseEntity.ok(cotisations);
}
    @GetMapping("/cotisations/{syndicId}")
    public List<CotisationProprietaireDTO> getCotisationsBySyndicId(@PathVariable Integer syndicId) {
        return service.findCotisationSyndic(syndicId);
    }

    @PostMapping("/addApp")
    public ResponseEntity<Appartement> addAppartement(@RequestBody Appartement request,
                                                      @RequestParam Long residenceId) {
        Appartement appartement = service.addAppartement(request, residenceId);
        return ResponseEntity.ok(appartement);
    }
    @GetMapping("/residence/{residenceId}")
    public ResponseEntity<List<Appartement>> getAppartementsByResidenceId(@PathVariable Long residenceId) {
        List<Appartement> appartements = service.getAppartementsByResidenceId(residenceId);
        return new ResponseEntity<>(appartements, HttpStatus.OK);
    }
    @GetMapping("/syndic/{syndicId}")
    public List<Appartement> findAppartementsBySyndicId(@PathVariable Long syndicId) {
        return service.findAppartementsBySyndicId(syndicId);
    }
}
