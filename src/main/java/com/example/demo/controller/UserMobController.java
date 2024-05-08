package com.example.demo.controller;

import com.example.demo.dto.UserMobDto;
import com.example.demo.model.*;
import com.example.demo.repository.UserMobRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserMobController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMobRepository userMobRepository;

    public UserMobController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<UserMob> addUser(@RequestBody UserMob newUser, @RequestParam(required = false) Long selectedApartmentId) {
        UserMob addedUser = userService.addUser(newUser, selectedApartmentId);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }


    @GetMapping("/users")
    public List<UserMobDto> getAllUsers() {
        return  userService.getUsers();
    }

    @GetMapping("/users/{syndicId}")
    public List<UserMobDto> getUsersBySyndicId(@PathVariable Long syndicId) {
        return userService.getUsersBySyndicId(syndicId);
    }


    @GetMapping("/agents")
    public ResponseEntity<List<UserMob>> getAllAgents() {
        List<UserMob> users = userService.getAllAgents();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public UserMob getUser(@PathVariable("id") int id){
        UserMob user = userService.getUserById(id);
        return user;
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserMob> updateUser(@PathVariable int userId,
                                              @RequestBody UserMob updatedUser,
                                              @RequestParam(required = false) Long apartmentId) {
        UserMob updatedUserData = userService.updateUser(userId, updatedUser, apartmentId);
        if (updatedUserData != null) {
            return ResponseEntity.ok(updatedUserData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }}
    @PutMapping("/{userId}/change-passwordUser")
    public ResponseEntity<?> changePassword(@PathVariable Integer userId, @RequestBody ChangePasswordRequest request) {
        UserMob userMob = userMobRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        String passkey = request.getCurrentPassword();
        if (!passwordEncoder.matches(passkey, userMob.getPasskey())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
        }
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        userMob.setPasskey(encodedNewPassword);
        userMobRepository.save(userMob);

        return new ResponseEntity<>(request, HttpStatus.OK);    }


}
