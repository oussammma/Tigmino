package com.example.demo.service;
import com.example.demo.dto.CotisationDto;
import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.dto.UserMobDto; // Import the UserMobDto class
import com.example.demo.model.Appartement;
import com.example.demo.model.CotisationApp;
import com.example.demo.model.Residence;
import com.example.demo.repository.AppartementRepository;
import com.example.demo.repository.ResidenceRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class appartementService {

    private final AppartementRepository appartementRepository;
    private final ResidenceRepository residenceRepository;

    public appartementService(AppartementRepository appartementRepository, ResidenceRepository residenceRepository) {
        this.appartementRepository = appartementRepository;
        this.residenceRepository = residenceRepository;
    }

    public List<Appartement> getAppartementsByResidenceId(Long residenceId) {
        return appartementRepository.findByResidenceId(residenceId);
    }
    public List<Appartement> findAppartementsBySyndicId(Long syndicId) {
        return appartementRepository.findByResidence_Syndic_Id(syndicId);
    }

    public List<Appartement> getAllApps() {
        return appartementRepository.findAll();
    }

    public List<CotisationApp> getCotisationsByProprietaireId(Long proprietaireId) {
        return appartementRepository.findByProprietaireId(proprietaireId);
    }
    public List<CotisationProprietaireDTO> findCotisationSyndic(Integer syndicId) {
        return appartementRepository.findCotisationSyndic(syndicId);
    }

    public Appartement addAppartement(Appartement request, Long residenceId) {
        Residence residence = residenceRepository.findById(residenceId)
                .orElseThrow(() -> new IllegalArgumentException("Residence not found with id: " + residenceId));

        Appartement appartement = new Appartement();
        appartement.setNumber(request.getNumber());
        appartement.setSuperficie(request.getSuperficie());
        appartement.setEtage(request.getEtage());
        appartement.setResidence(residence);
        return appartementRepository.save(appartement);
    }
}
