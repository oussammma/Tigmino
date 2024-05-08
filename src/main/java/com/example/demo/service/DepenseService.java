package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.BlocDepenseRepository;
import com.example.demo.repository.BlocRepository;
import com.example.demo.repository.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepenseService {

    @Autowired
    private DepenseRepository depenseRepository;
    @Autowired
    private BlocRepository blocRepository;
    @Autowired
    private BlocDepenseRepository blocDepenseRepository;


    public List<Depense> getAllDepensesWithBlocsAndResidences() {
        return depenseRepository.findAllWithBlocsAndResidences();
    }
    public List<Depense> getAllDepensesForResidence(Long residenceId) {
        return depenseRepository.findAllByResidenceId(residenceId);
    }
    public Depense saveDepense(Depense request, List<Long> blocIds) {
        Depense depense = new Depense();
        depense.setDescription(request.getDescription());
        depense.setMode(request.getMode());
        depense.setObjectif(request.getObjectif());
        depense.setPreuvePaiementPath(request.getPreuvePaiementPath());
        depense.setMontantTotal(request.getMontantTotal());
        depense.setDatePaiement(request.getDatePaiement());
        depense.setDateDebut(request.getDateDebut());
        depense.setDateFin(request.getDateFin());

        Depense savedDepense = depenseRepository.save(depense);
        for (Long blocId : blocIds) {
            Bloc bloc = blocRepository.findById(blocId).orElse(null);
            if (bloc != null) {
                BlocDepense blocDepense = new BlocDepense();
                blocDepense.setDepense(savedDepense);
                blocDepense.setBloc(bloc);
                blocDepenseRepository.save(blocDepense);
            }
        }

        return savedDepense;
    }
    public Depense markDepenseCompleted(Integer depenseId, String comment) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Depense not found with id: " + depenseId));

        depense.setTypeDepense(TypeExp.completed);
        depense.setCommentaire(comment);
        return depenseRepository.save(depense);
    }


    public Depense markDepenseCancelled(Integer depenseId, String comment) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new RuntimeException("Depense not found with id: " + depenseId));

        depense.setTypeDepense(TypeExp.cancelled);
        depense.setCommentaire(comment); // Set comment
        return depenseRepository.save(depense);
    }
}
