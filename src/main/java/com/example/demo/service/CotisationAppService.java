package com.example.demo.service;

import com.example.demo.model.Appartement;
import com.example.demo.model.CotisationApp;
import com.example.demo.repository.AppartementRepository;
import com.example.demo.repository.CotisationAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CotisationAppService {

    @Autowired
    private CotisationAppRepository cotisationAppRepository;
    @Autowired
    private AppartementRepository appartementRepository;

    public CotisationApp addCotisationApp(CotisationApp cotisationApp, Long appartementId) {
        Appartement appartement = appartementRepository.findById(appartementId).orElse(null);
        if (appartement == null) {
            throw new IllegalArgumentException("Invalid appartementId");
        }
        cotisationApp.setAppartement(appartement);
        return cotisationAppRepository.save(cotisationApp);
    }
}