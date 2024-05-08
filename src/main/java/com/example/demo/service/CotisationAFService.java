package com.example.demo.service;

import com.example.demo.dto.CotisationAFProprietaireDTO;
import com.example.demo.dto.CotisationProprietaireDTO;
import com.example.demo.model.Appartement;
import com.example.demo.model.AppelDeFonds;
import com.example.demo.model.CotisationAF;
import com.example.demo.model.CotisationApp;
import com.example.demo.repository.AppartementRepository;
import com.example.demo.repository.AppelDeFondsRepository;
import com.example.demo.repository.CotisationAFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotisationAFService {

    private final CotisationAFRepository cotisationAFRepository;
    @Autowired
    private AppartementRepository appartementRepository;

    @Autowired
    private AppelDeFondsRepository appelDeFondsRepository;

    @Autowired
    public CotisationAFService(CotisationAFRepository cotisationAFRepository) {
        this.cotisationAFRepository = cotisationAFRepository;
    }


    public double getTotalMontantByAppelDeFondsId(Long appelDeFondsId) {
        return cotisationAFRepository.getTotalMontantByAppelDeFondsId(appelDeFondsId);
    }

    public List<CotisationAFProprietaireDTO> getCotisationAFsByAppelDeFondsId(Long appelDeFondsId) {
        return cotisationAFRepository.findByAppelDeFondsId(appelDeFondsId);
    }

    public List<CotisationAFProprietaireDTO> getCotisationsForCompletedAppelDeFonds(Integer syndicId) {
        return cotisationAFRepository.findByCompletedAppelDeFonds(syndicId);
    }

    public CotisationAF addCotisationAf(CotisationAF cotisationAf, Long appartementId, Long appelDeFondsId) {
        Appartement appartement = appartementRepository.findById(appartementId).orElse(null);
        if (appartement == null) {
            throw new IllegalArgumentException("Invalid appartementId");
        }
        AppelDeFonds appelDeFonds = appelDeFondsRepository.findById(appelDeFondsId).orElse(null);
        if (appelDeFonds == null) {
            throw new IllegalArgumentException("Invalid appelDeFondsId");
        }
        cotisationAf.setAppartement(appartement);
        cotisationAf.setAppelDeFonds(appelDeFonds);
        return cotisationAFRepository.save(cotisationAf);
    }


}
