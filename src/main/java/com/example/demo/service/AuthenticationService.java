package com.example.demo.service;

import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.Token;
import com.example.demo.model.TokenType;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final WebAdminRepository repository;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(WebAdminRepository repository, TokenRepository tokenRepo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(WebAdmin request){
        WebAdmin admin = new WebAdmin();
        admin.setNom(request.getNom());
        admin.setPrenom(request.getPrenom());
        admin.setContact(request.getContact());
        admin.setPasskey(passwordEncoder.encode(request.getPasskey()));
        admin.setPseudoname(request.getPseudoname());
        admin.setDept_code(request.getDept_code());
        admin.setRole(request.getRole());
        admin.setComplexity_flag(request.getComplexity_flag());
        admin.setDate_end_passkey(request.getDate_end_passkey());
        admin.setDate_start_passkey(request.getDate_start_passkey());
        admin.setIP_address(request.getIP_address());
        admin.setConnected(request.getConnected());
        admin.setLast_4_passkeys(request.getLast_4_passkeys());
        admin.setNbr_session_allowed(request.getNbr_session_allowed());
        admin.setLast_4_passkeys(request.getLast_4_passkeys());
        admin.setExpiration_password(request.getExpiration_password());
        admin.setLength_passkey(request.getLength_passkey());
        admin.setNumber_of_tries(request.getNumber_of_tries());
        admin.setBlock_access(request.getBlock_access());
        admin.setNumber_of_tries_allowed(request.getNumber_of_tries_allowed());
        admin.setNumber_session_connected(request.getNumber_session_connected());
        admin.setLanguage_code(request.getLanguage_code());
        admin.setIP_address_mang(request.getIP_address_mang());

        var savedAdmin = repository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        savedAdminToken(savedAdmin, jwtToken);
        return new AuthenticationResponse(jwtToken,admin, "Admin already exist");
    }

    public AuthenticationResponse authenticate(WebAdmin request) {
        String pseudoname = request.getPseudoname();
        String passkey = request.getPasskey();
        WebAdmin admin = repository.findByPseudoname(pseudoname)
                .orElseThrow(() -> new RuntimeException("Administrateur introuvable"));

        if (passwordEncoder.matches(passkey, admin.getPasskey())) {
            String token = jwtService.generateToken(admin);
            savedAdminToken(admin, token);
            revokeAllAdminTokens(admin);
            return new AuthenticationResponse(token, admin, "Login successful");
        } else {
            throw new RuntimeException("Authentification échouée. Informations invalides.");
        }
    }
    private void revokeAllAdminTokens(WebAdmin admin){
        var validToken = tokenRepo.findAllValidTokensByAdmin(admin.getId());
        if(validToken.isEmpty())
            return;
        validToken.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepo.saveAll(validToken);
    }
    private void savedAdminToken(WebAdmin admin, String jwtToken) {
        var token = new Token(0, jwtToken, TokenType.BEARER, false, false, admin, null);
        tokenRepo.save(token);
    }


}
