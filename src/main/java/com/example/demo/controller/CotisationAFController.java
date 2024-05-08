package com.example.demo.controller;

import com.example.demo.dto.CotisationAFProprietaireDTO;
import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.model.CotisationAF;
import com.example.demo.model.CotisationApp;
import com.example.demo.service.CotisationAFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cotisations-af")
public class CotisationAFController {

    private final CotisationAFService cotisationAFService;

    @Autowired
    public CotisationAFController(CotisationAFService cotisationAFService) {
        this.cotisationAFService = cotisationAFService;
    }

    @GetMapping("/appel-de-fonds/{appelDeFondsId}")
    public List<CotisationAFProprietaireDTO> getCotisationAFsByAppelDeFondsId(@PathVariable Long appelDeFondsId) {
        return cotisationAFService.getCotisationAFsByAppelDeFondsId(appelDeFondsId);
    }
    @GetMapping("/total-montant/{appelDeFondsId}")
    public ResponseEntity<Double> getTotalMontantByAppelDeFondsId(@PathVariable Long appelDeFondsId) {
        double totalMontant = cotisationAFService.getTotalMontantByAppelDeFondsId(appelDeFondsId);
        return new ResponseEntity<>(totalMontant, HttpStatus.OK);
    }

    @PostMapping("/add/{appartementId}/{appelDeFondsId}")
    public ResponseEntity<CotisationAF> addCotisationAF(@RequestBody CotisationAF cotisationAf,
                                                        @PathVariable Long appartementId,
                                                        @PathVariable Long appelDeFondsId) {
        CotisationAF createdCotisationAf = cotisationAFService.addCotisationAf(cotisationAf, appartementId, appelDeFondsId);
        return new ResponseEntity<>(createdCotisationAf, HttpStatus.CREATED);
    }

    @GetMapping("/completed/{syndicId}")
    public List<CotisationAFProprietaireDTO> getCotisationsForCompletedAppelDeFonds(@PathVariable Integer syndicId) {
        return cotisationAFService.getCotisationsForCompletedAppelDeFonds(syndicId);
    }

}
