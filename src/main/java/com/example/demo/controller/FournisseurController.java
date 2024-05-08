package com.example.demo.controller;

import com.example.demo.model.FileDB;
import com.example.demo.model.Fournisseur;
import com.example.demo.model.FournisseurRequest;
import com.example.demo.model.Responsable;
import com.example.demo.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        return new ResponseEntity<>(fournisseurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable("id") Long id) {
        Optional<Fournisseur> fournisseur = fournisseurService.getFournisseurById(id);
        return fournisseur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Fournisseur> createFournisseur(@RequestPart FournisseurRequest fournisseurRequest,
                                   @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<FileDB> images = uploadImage(file);
            Fournisseur fournisseur = fournisseurRequest.getFournisseur();
            fournisseur.setLogo(images);
            Set<Responsable> responsables = fournisseurRequest.getResponsables();
            responsables.forEach(responsable -> responsable.setFournisseur(fournisseur));
            fournisseur.setResponsables(responsables);
            Fournisseur savedFournisseur = fournisseurService.saveOrUpdateFournisseur(fournisseur);
            return new ResponseEntity<>(savedFournisseur, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    public Set<FileDB> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<FileDB> images = new HashSet<>();

        for(MultipartFile file: multipartFiles){
            FileDB image = new FileDB(
                    file.getName(),
                    file.getContentType(),
                    file.getBytes());
            images.add(image);
        }
        return images;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable("id") Long id, @RequestBody FournisseurRequest fournisseurRequest) {
        Optional<Fournisseur> existingFournisseurOptional = fournisseurService.getFournisseurById(id);
        if (existingFournisseurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Fournisseur existingFournisseur = getExistingFournisseur(fournisseurRequest, existingFournisseurOptional);
        // Assuming the logo update is handled separately if needed

        // Update the responsables
        Set<Responsable> responsables = fournisseurRequest.getResponsables();
        responsables.forEach(responsable -> responsable.setFournisseur(existingFournisseur));
        existingFournisseur.getResponsables().clear(); // Clear existing responsables
        existingFournisseur.getResponsables().addAll(responsables); // Add updated responsables

        // Save the updated Fournisseur
        Fournisseur updatedFournisseur = fournisseurService.saveOrUpdateFournisseur(existingFournisseur);
        return new ResponseEntity<>(updatedFournisseur, HttpStatus.OK);
    }

    private static Fournisseur getExistingFournisseur(FournisseurRequest fournisseurRequest, Optional<Fournisseur> existingFournisseurOptional) {
        Fournisseur existingFournisseur = existingFournisseurOptional.get();

        // Update the fields from the request
        Fournisseur fournisseur = fournisseurRequest.getFournisseur();
        existingFournisseur.setNom(fournisseur.getNom());
        existingFournisseur.setService(fournisseur.getService());
        existingFournisseur.setAdresse(fournisseur.getAdresse());
        existingFournisseur.setIdFiscal(fournisseur.getIdFiscal());
        existingFournisseur.setICE(fournisseur.getICE());
        return existingFournisseur;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable("id") Long id) {
        fournisseurService.deleteFournisseurById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
