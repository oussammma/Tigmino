package com.example.demo.controller;

import com.example.demo.model.AuthenticationResponseUser;
import com.example.demo.model.UserMob;
import com.example.demo.model.WebAdmin;
import com.example.demo.service.LogoutService;
import com.example.demo.service.TokenBlacklistService;
import com.example.demo.service.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationUserController {

    @Autowired
    private UserAuthentication authuserService;
    private TokenBlacklistService tokenBlacklistService;
    @Autowired
    private final LogoutService logoutService;

    public AuthenticationUserController(UserAuthentication authService, LogoutService logoutService) {
        this.authuserService = authService;
        this.logoutService = logoutService;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<AuthenticationResponseUser> loginUser(@RequestBody UserMob request){
        AuthenticationResponseUser response = authuserService.authenticate(request);
        return ResponseEntity.ok(response);
    }



}
