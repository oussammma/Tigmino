package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.UserMobRepository;
import com.example.demo.repository.UserTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthentication {
    private final UserMobRepository repository;
    private final UserTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthentication(UserMobRepository repository, UserTokenRepository tokenRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseUser authenticate(UserMob request) {
        String pseudoname = request.getPseudoname();
        String passkey = request.getPasskey();
        UserMob user = repository.findByPseudoname(pseudoname)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Residence residence = user.getAppartement().getResidence();
        if (passwordEncoder.matches(passkey, user.getPasskey())) {
            String token = jwtService.generateTokenUser(user);
            savedUserToken(user, token);
            revokeAllUserTokens(user);
            return new AuthenticationResponseUser(token, user, "Login successful",residence);
        } else {
            throw new RuntimeException("Authentification échouée. Informations invalides.");
        }
    }
    private void revokeAllUserTokens(UserMob user){
        var validToken = tokenRepo.findAllValidTokensByUser(user.getId());
        if(validToken.isEmpty())
            return;
        validToken.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepo.saveAll(validToken);
    }

    private void savedUserToken(UserMob user, String jwtToken) {
        var token = new Token(0,jwtToken, TokenType.BEARER, false, false,null, user);
        tokenRepo.save(token);
    }

}
