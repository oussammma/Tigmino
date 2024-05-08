package com.example.demo.controller;

import com.example.demo.model.ChangePasswordRequest;
import com.example.demo.model.WebAdmin;
import com.example.demo.dto.WebAdminDTO;
import com.example.demo.repository.WebAdminRepository;
import com.example.demo.service.AdminWebServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WebAdminController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    WebAdminRepository webAdminRepository;
    @Autowired
    AdminWebServiceImp service;

    public WebAdminController(WebAdminRepository webAdminRepository) {
        this.webAdminRepository = webAdminRepository;
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<WebAdmin> updateWebAdmin(@PathVariable int id, @RequestBody WebAdminDTO updateDTO) {
        try {
            Optional<WebAdmin> optionalWebAdmin = webAdminRepository.findById(id);
            if (optionalWebAdmin.isPresent()) {
                WebAdmin existingWebAdmin = optionalWebAdmin.get();
                existingWebAdmin.setPseudoname(updateDTO.getPseudoname());
                existingWebAdmin.setRole(updateDTO.getRole());
                existingWebAdmin.setEmail(updateDTO.getEmail());
                existingWebAdmin.setNom(updateDTO.getNom());
                existingWebAdmin.setPrenom(updateDTO.getPrenom());
                existingWebAdmin.setContact(updateDTO.getContact());
               WebAdmin updatedWebAdmin = webAdminRepository.save(existingWebAdmin);

                return ResponseEntity.ok(updatedWebAdmin);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }}
    @PutMapping("/{adminId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Integer adminId, @RequestBody ChangePasswordRequest request) {
        WebAdmin admin = webAdminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        String passkey = request.getCurrentPassword();
        if (!passwordEncoder.matches(passkey, admin.getPasskey())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
        }
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        admin.setPasskey(encodedNewPassword);
        webAdminRepository.save(admin);

        return new ResponseEntity<>(request, HttpStatus.OK);    }
    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<WebAdmin>> getAllAdmins() {
            List<WebAdmin> admins = new ArrayList<>();
            admins = service.getAllAdmins();
            return new ResponseEntity<>(admins, HttpStatus.OK);

    }

    @GetMapping("/syndics")
    public ResponseEntity<List<WebAdmin>> findSyndics() {
        List<WebAdmin> syndics = new ArrayList<>();
        syndics = webAdminRepository.getAllSyndics();
        return new ResponseEntity<>(syndics, HttpStatus.OK);

    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable("id") int id) {
        service.deleteAdminById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admins")
    public ResponseEntity<HttpStatus> deleteAllAdmins() {
        try {
            webAdminRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("admin/{id}")
    public ResponseEntity<Optional<WebAdmin>> getAdminById(@PathVariable int id) {
        Optional<WebAdmin> admin = Optional.ofNullable(service.getAdminById(id));
        return ResponseEntity.ok(admin);
    }

}
