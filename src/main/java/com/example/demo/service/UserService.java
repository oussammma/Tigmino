package com.example.demo.service;

import com.example.demo.dto.ReclamationDTO;
import com.example.demo.dto.UserMobDto;
import com.example.demo.model.*;
import com.example.demo.repository.AppartementRepository;
import com.example.demo.repository.ResidenceRepository;
import com.example.demo.repository.UserMobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMobRepository repository;
    private final AppartementRepository appartementRepository;
    private final ResidenceRepository residenceRepository;

    public UserService(UserMobRepository repository, AppartementRepository appartementRepository,
                       ResidenceRepository residenceRepository) {
        this.repository = repository;
        this.appartementRepository = appartementRepository;
        this.residenceRepository = residenceRepository;
    }
    public List<UserMobDto> getUsers() {
        List<UserMob> userMobs = repository.findAllWithAppart();
        return userMobs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public UserMob addUser(UserMob request, Long apartmentId) {
        UserMob user = new UserMob();
        user.setCin(request.getCin());
        user.setNom(request.getNom());
        user.setFonction(request.getFonction());
        user.setDateNaissance(request.getDateNaissance());
        user.setPrenom(request.getPrenom());
        user.setSexe(request.getSexe());
        user.setPseudoname(request.getPseudoname());
        user.setPasskey(request.getPasskey());
        user.setTel(request.getTel());
        user.setType(request.getType());
        user.setBlockAccess(request.getBlockAccess());
        user.setNumberOfTriesAllowed(request.getNumberOfTriesAllowed());
        if (apartmentId != null) {
            Appartement apartment = appartementRepository.findById(apartmentId)
                    .orElseThrow(() -> new RuntimeException("Apartment not found"));
            user.setAppartement(apartment);
        }
        user = repository.save(user);
        return user;
    }

    public List<UserMob> getAllAgents() {
        return repository.findAllAgents();
    }
    public void deleteUserById(int id) {
        repository.deleteById(id);
    }


    public UserMob getUserById(int userId) {
        return repository.findByUserId(userId);
    }

    public List<UserMobDto> getAllUsers() {
        List<UserMob> userMobs = repository.findAll();
        return userMobs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserMobDto convertToDto(UserMob userMob) {
        UserMobDto userMobDto = new UserMobDto();
        userMobDto.setId(userMob.getId());
        userMobDto.setCin(userMob.getCin());
        userMobDto.setPseudoname(userMob.getPseudoname());
        userMobDto.setNom(userMob.getNom());
        userMobDto.setPrenom(userMob.getPrenom());
        userMobDto.setTel(userMob.getTel());
        userMobDto.setDateNaissance(userMob.getDateNaissance());
        userMobDto.setSexe(userMob.getSexe());
        userMobDto.setType(userMob.getType());

        // Include appartement information if available
        if (userMob.getAppartement() != null) {
            userMobDto.setAppartementId(userMob.getAppartement().getId());
            userMobDto.setAppartementNumber(userMob.getAppartement().getNumber());
            userMobDto.setResidenceId(userMob.getAppartement().getResidence().getId());
            userMobDto.setResidenceNom(userMob.getAppartement().getResidence().getNom());
        }

        return userMobDto;
    }

    public List<UserMobDto> getUsersBySyndicId(Long syndicId) {
        List<UserMob> users = repository.findBySyndicId(syndicId);
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserMob updateUser(int userId, UserMob updatedUser, Long apartmentId) {
        UserMob existingUser = repository.findByUserId(userId);
        existingUser.setCin(updatedUser.getCin());
        existingUser.setNom(updatedUser.getNom());
        existingUser.setFonction(updatedUser.getFonction());
        existingUser.setDateNaissance(updatedUser.getDateNaissance());
        existingUser.setPrenom(updatedUser.getPrenom());
        existingUser.setSexe(updatedUser.getSexe());
        existingUser.setPseudoname(updatedUser.getPseudoname());
        existingUser.setTel(updatedUser.getTel());
        existingUser.setType(updatedUser.getType());
        existingUser.setBlockAccess(updatedUser.getBlockAccess());
        existingUser.setNumberOfTriesAllowed(updatedUser.getNumberOfTriesAllowed());

        if (apartmentId != null) {
            Appartement apartment = appartementRepository.findById(apartmentId)
                    .orElseThrow(() -> new RuntimeException("Apartment not found"));
            existingUser.setAppartement(apartment);
        }
        return repository.save(existingUser);
    }

}
