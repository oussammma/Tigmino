package com.example.demo.service;

import com.example.demo.dto.RecDt;
import com.example.demo.model.Depense;
import com.example.demo.model.Reclamation;
import com.example.demo.dto.ReclamationDTO;
import com.example.demo.model.Statut;
import com.example.demo.model.TypeExp;
import com.example.demo.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<ReclamationDTO> getAllReclamationsWithUserNames(Long syndicId) {
        List<Reclamation> reclamations = reclamationRepository.findBySyndicId(syndicId);
        return getReclamationDTOS(reclamations);
    }
    public List<ReclamationDTO> getAllRec() {
        List<Reclamation> reclamations = reclamationRepository.findAllRec();
        return getReclamationDTOS(reclamations);
    }
    public List<RecDt> getAllRecs() {
        List<Reclamation> reclamations = reclamationRepository.findAllRec();
        return getRecDts(reclamations);
    }
    private List<RecDt> getRecDts(List<Reclamation> reclamations) {
        List<RecDt> reclamationDTOs = new ArrayList<>();

        for (Reclamation reclamation : reclamations) {
            RecDt dto = new RecDt();
            dto.setId(reclamation.getId());
            dto.setTitre(reclamation.getTitre());
            dto.setDescription(reclamation.getDescription());
            dto.setDateReclamation(reclamation.getDateReclamation());
            dto.setNom(reclamation.getMobileUser().getNom());
            dto.setPrenom(reclamation.getMobileUser().getPrenom());
            dto.setApprt_number(reclamation.getMobileUser().getAppartement().getNumber());
            dto.setStatut(reclamation.getStatut());
            dto.setTreated(reclamation.isTreated());
            dto.setCommentaire(reclamation.getCommentaire());
            dto.setResidence(reclamation.getMobileUser().getAppartement().getResidence().getNom());
            reclamationDTOs.add(dto);
        }

        return reclamationDTOs;
    }

    private List<ReclamationDTO> getReclamationDTOS(List<Reclamation> reclamations) {
        List<ReclamationDTO> reclamationDTOs = new ArrayList<>();

        for (Reclamation reclamation : reclamations) {
            ReclamationDTO dto = new ReclamationDTO();
            dto.setId(reclamation.getId());
            dto.setTitre(reclamation.getTitre());
            dto.setDescription(reclamation.getDescription());
            dto.setDateReclamation(reclamation.getDateReclamation());
            dto.setNom(reclamation.getMobileUser().getNom());
            dto.setPrenom(reclamation.getMobileUser().getPrenom());
            dto.setApprt_number(reclamation.getMobileUser().getAppartement().getNumber());
            dto.setStatut(reclamation.getStatut());
            dto.setTreated(reclamation.isTreated());
            dto.setCommentaire(reclamation.getCommentaire());
            //dto.setResidence(reclamation.getMobileUser().getAppartement().getResidence().getNom());
            reclamationDTOs.add(dto);
        }

        return reclamationDTOs;
    }

    public ReclamationDTO getReclamationById(Integer id) {
        Reclamation reclamation = reclamationRepository.findById(id).orElse(null);
        if (reclamation == null) {
            return null;
        }
        ReclamationDTO reclamationDTO = new ReclamationDTO();
        reclamationDTO.setId(reclamation.getId());
        reclamationDTO.setTitre(reclamation.getTitre());
        reclamationDTO.setDescription(reclamation.getDescription());
        reclamationDTO.setDateReclamation(reclamation.getDateReclamation());
        reclamationDTO.setNom(reclamation.getMobileUser().getNom());
        reclamationDTO.setPrenom(reclamation.getMobileUser().getPrenom());
        reclamationDTO.setApprt_number(reclamation.getMobileUser().getAppartement().getNumber());
        reclamationDTO.setStatut(reclamation.getStatut());
        reclamationDTO.setTreated(reclamation.isTreated());
        reclamationDTO.setCommentaire(reclamation.getCommentaire());
        return reclamationDTO;
    }

    public List<Object[]> getReclamationsByStatut(Statut statut) {
        return reclamationRepository.findByStatut(statut);
    }
    public ReclamationDTO markReclamationCompleted(Integer reclamationId, String comment) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));

        reclamation.setTreated(true);
        reclamation.setCommentaire(comment);
        reclamationRepository.save(reclamation);
        ReclamationDTO reclamationDTO = new ReclamationDTO();
        reclamationDTO.setId(reclamation.getId());
        reclamationDTO.setTitre(reclamation.getTitre());
        reclamationDTO.setDescription(reclamation.getDescription());
        reclamationDTO.setDateReclamation(reclamation.getDateReclamation());
        reclamationDTO.setNom(reclamation.getMobileUser().getNom());
        reclamationDTO.setPrenom(reclamation.getMobileUser().getPrenom());
        reclamationDTO.setApprt_number(reclamation.getMobileUser().getAppartement().getNumber());
        reclamationDTO.setStatut(reclamation.getStatut());
        reclamationDTO.setTreated(reclamation.isTreated());
        reclamationDTO.setCommentaire(reclamation.getCommentaire());

        return reclamationDTO;
    }

}
