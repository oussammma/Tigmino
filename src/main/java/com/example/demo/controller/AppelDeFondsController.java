package com.example.demo.controller;
import com.example.demo.model.AppelDeFonds;
import com.example.demo.model.CotisationAF;
import com.example.demo.model.TypeExp;
import com.example.demo.repository.AppelDeFondsRepository;
import com.example.demo.service.AppelDeFondsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appels")
public class AppelDeFondsController {

    @Autowired
    private AppelDeFondsService appelDeFondsService;

    @Autowired
    private AppelDeFondsRepository appelDeFondsRepository;

    @GetMapping("/")
    public List<AppelDeFonds> getAllAppels() {
        return appelDeFondsRepository.findAll();
    }

    @GetMapping("/user/{userMobId}")
    public List<AppelDeFonds> getAppelDeFondsByUserMobId(@PathVariable("userMobId") Long userMobId) {
        return appelDeFondsRepository.findByUserMobId(userMobId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppelDeFonds> getAppelDeFondsById(@PathVariable("id") Long id) {
        Optional<AppelDeFonds> appelDeFonds = appelDeFondsService.getAppelDeFondsById(id);
        return appelDeFonds.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-syndic/{syndicId}")
    public ResponseEntity<List<AppelDeFonds>> getAppelDeFondsBySyndicId(@PathVariable("syndicId") Long syndicId) {
        List<AppelDeFonds> appelDeFondsList = appelDeFondsService.getAppelDeFondsBySyndicId(syndicId);
        return ResponseEntity.ok(appelDeFondsList);
    }

    @PostMapping("/add/{syndicId}")
    public ResponseEntity<AppelDeFonds> addAppelDeFonds(@RequestBody AppelDeFonds appelDeFonds, @PathVariable Integer syndicId) {
        AppelDeFonds newAppelDeFonds = appelDeFondsService.addAppelDeFonds(appelDeFonds, syndicId);
        return ResponseEntity.ok().body(newAppelDeFonds);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppelDeFonds> updateAppelDeFonds(@PathVariable("id") Long id, @RequestBody AppelDeFonds updatedAppelDeFonds) {
        AppelDeFonds updated = appelDeFondsService.updateAppelDeFonds(id, updatedAppelDeFonds);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppelDeFonds(@PathVariable("id") Long id) {
        appelDeFondsService.deleteAppelDeFonds(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> markAsCompleted(@PathVariable Long id, @RequestBody AppelDeFonds updatedAppelDeFonds) {
        Optional<AppelDeFonds> optionalAppelDeFonds = appelDeFondsRepository.findById(id);
        if (optionalAppelDeFonds.isPresent()) {
            AppelDeFonds appelDeFonds = optionalAppelDeFonds.get();
            appelDeFonds.setStatut(TypeExp.completed);
            appelDeFonds.setMontant(updatedAppelDeFonds.getMontant());
            appelDeFonds.setPreuvePaiement(updatedAppelDeFonds.getPreuvePaiement());
            appelDeFondsRepository.save(appelDeFonds);
            return ResponseEntity.ok("Appel de fonds marked as completed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appel de fonds not found.");
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> markAsCancelled(@PathVariable Long id, @RequestBody AppelDeFonds updatedAppelDeFonds) {
        Optional<AppelDeFonds> optionalAppelDeFonds = appelDeFondsRepository.findById(id);
        if (optionalAppelDeFonds.isPresent()) {
            AppelDeFonds appelDeFonds = optionalAppelDeFonds.get();
            appelDeFonds.setStatut(TypeExp.cancelled);
            appelDeFonds.setCommentaire(updatedAppelDeFonds.getCommentaire());
            appelDeFondsRepository.save(appelDeFonds);
            return ResponseEntity.ok("Appel de fonds marked as cancelled.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appel de fonds not found.");
        }
    }

}
