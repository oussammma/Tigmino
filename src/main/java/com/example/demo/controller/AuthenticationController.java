package com.example.demo.controller;

import com.example.demo.model.WebAdmin;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.LogoutService;
import com.example.demo.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;
    private TokenBlacklistService tokenBlacklistService;
    @Autowired
    private final LogoutService logoutService;

    public AuthenticationController(AuthenticationService authService, TokenBlacklistService tokenBlacklistService, LogoutService logoutService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.logoutService = logoutService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody WebAdmin request
    ){
        AuthenticationResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody WebAdmin request ) {
        return ResponseEntity.ok(authService.register(request));
    }


}
