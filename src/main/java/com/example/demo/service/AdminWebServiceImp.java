package com.example.demo.service;

import com.example.demo.model.PasswordResetToken;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.PasswordTokenRepository;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminWebServiceImp implements AdminWebService {
    @Autowired
    private WebAdminRepository webAdminRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<WebAdmin> getAllAdmins() {
        return webAdminRepository.findAll();

    }

    @Override
    public void deleteAdminById(int id) {
        webAdminRepository.deleteById(id);


    }

    public WebAdmin getAdminById(int id) {
        return webAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
    }
    public void createPasswordResetTokenForUser(WebAdmin admin, String token) {
        try {
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            myToken.setAdmin(admin);
            myToken.setExpiryDate(calculateExpiryDate());
            passwordTokenRepository.save(myToken);
        } catch (Exception e) {
            System.err.println("Error saving password reset token: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
        return calendar.getTime();
    }
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }


    public void changeUserPassword(WebAdmin admin, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        admin.setPasskey(encodedPassword);
        webAdminRepository.save(admin);
    }


    public Optional<WebAdmin> getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        if (passwordResetToken != null) {
            return Optional.of(passwordResetToken.getAdmin());
        } else {
            return Optional.empty();
        }
    }
}
