package com.example.demo.controller;

import com.example.demo.dto.AddCommentRequest;
import com.example.demo.dto.ReclamationDTO;
import com.example.demo.model.Commentaire;
import com.example.demo.model.CommentaireInfo;
import com.example.demo.model.Reclamation;
import com.example.demo.model.UserMob;
import com.example.demo.repository.CommentaireRepository;
import com.example.demo.service.CommentaireService;
import com.example.demo.service.ReclamationService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;
    private final ReclamationService reclamationService;
    private final UserService userService;

    private final CommentaireRepository commentaireRepo;

    @Autowired
    public CommentaireController(CommentaireService commentaireService, ReclamationService reclamationService, UserService userService, CommentaireRepository commentaireRepo) {
        this.commentaireService = commentaireService;
        this.reclamationService = reclamationService;
        this.userService = userService;
        this.commentaireRepo = commentaireRepo;
    }
    @PostMapping("/addComment")
    public ResponseEntity<Commentaire> addCommentToReclamation(@RequestBody AddCommentRequest request) {
        UserMob userMob = userService.getUserById(request.getUserMobId());
        if (userMob == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ReclamationDTO reclamationDTO = reclamationService.getReclamationById(request.getReclamationId());
        if (reclamationDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reclamation reclamation = mapToReclamation(reclamationDTO);

        Commentaire commentaire = commentaireService.addCommentToReclamation(userMob, reclamation, request.getContenu());
        return new ResponseEntity<>(commentaire, HttpStatus.CREATED);
    }

    private Reclamation mapToReclamation(ReclamationDTO reclamationDTO) {
        Reclamation reclamation = new Reclamation();
        reclamation.setId(reclamationDTO.getId());
        reclamation.setTitre(reclamationDTO.getTitre());
        reclamation.setDescription(reclamationDTO.getDescription());
        reclamation.setDateReclamation(reclamationDTO.getDateReclamation());
        reclamation.setTreated(reclamationDTO.isTreated());
        reclamation.setCommentaire(reclamationDTO.getCommentaire());
        reclamation.setStatut(reclamationDTO.getStatut());
        return reclamation;
    }


    @GetMapping("/reclamation/{reclamationId}")
    public List<CommentaireInfo> getCommentairesByReclamationId(@PathVariable Integer reclamationId) {
        return commentaireRepo.findCommentairesInfoByReclamationId(reclamationId);
    }


}
