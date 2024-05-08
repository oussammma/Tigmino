package com.example.demo.service;

import com.example.demo.model.Residence;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.ResidenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResidenceService {

    private final ResidenceRepository residenceRepository;

    @Autowired
    public ResidenceService(ResidenceRepository residenceRepository) {
        this.residenceRepository = residenceRepository;
    }

    public List<Residence> getAll() {
        return residenceRepository.findAll();
    }
    @Transactional
    public void updateResidenceSyndicId(Long residenceId, Integer newSyndicId) {
        Optional<Residence> optionalResidence = residenceRepository.findById(residenceId);
        optionalResidence.ifPresent(residence -> {
            WebAdmin newSyndic = new WebAdmin();
            newSyndic.setId(newSyndicId);
            residence.setSyndic(newSyndic);
            residenceRepository.save(residence);
        });
    }

    public Residence updateResidence(Long id, Residence updatedResidence) {
        Optional<Residence> optionalResidence = residenceRepository.findById(id);
        if (optionalResidence.isPresent()) {
            Residence residence = optionalResidence.get();
            residence.setNom(updatedResidence.getNom());
            residence.setAdresse(updatedResidence.getAdresse());
            residence.setCodeRes(updatedResidence.getCodeRes());
            return residenceRepository.save(residence);
        }
        return null;
    }
}
