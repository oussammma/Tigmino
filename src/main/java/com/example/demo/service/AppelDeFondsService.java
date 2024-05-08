package com.example.demo.service;

import com.example.demo.model.AppelDeFonds;
import com.example.demo.model.CotisationAF;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.AppelDeFondsRepository;
import com.example.demo.repository.WebAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppelDeFondsService {

    @Autowired
    private AppelDeFondsRepository appelDeFondsRepository;
    @Autowired
    private WebAdminRepository webAdminRepository;

    public Optional<AppelDeFonds> getAppelDeFondsById(Long id) {
        return appelDeFondsRepository.findById(id);
    }

    public List<AppelDeFonds> getAppelDeFondsBySyndicId(Long syndicId) {
        return appelDeFondsRepository.findBySyndicId(syndicId);
    }

    public AppelDeFonds addAppelDeFonds(AppelDeFonds appelDeFonds, Integer syndicId) {
        WebAdmin syndic = webAdminRepository.findById(syndicId).orElseThrow(() -> new EntityNotFoundException("Syndic not found with id: " + syndicId));
        appelDeFonds.setSyndic(syndic);
        return appelDeFondsRepository.save(appelDeFonds);
    }


    public AppelDeFonds updateAppelDeFonds(Long id, AppelDeFonds updatedAppelDeFonds) {
        if (appelDeFondsRepository.existsById(id)) {
            updatedAppelDeFonds.setId(id); // Ensure the correct ID is set
            return appelDeFondsRepository.save(updatedAppelDeFonds);
        } else {
            throw new EntityNotFoundException("AppelDeFonds not found with id: " + id);
        }
    }

    public void deleteAppelDeFonds(Long id) {
        appelDeFondsRepository.deleteById(id);
    }

}
