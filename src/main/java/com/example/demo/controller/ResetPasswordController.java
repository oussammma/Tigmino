package com.example.demo.controller;
import com.example.demo.dto.PasswordDto;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.PasswordTokenRepository;
import com.example.demo.model.GenericResponse;
import com.example.demo.service.AdminWebServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ResetPasswordController {

    @Autowired
    private AdminWebServiceImp service;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private WebAdminRepository repo;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String adminEmail) {
        WebAdmin admin = repo.findByEmail(adminEmail).orElse(null);
        if (admin == null) {
            throw new UserNotFoundException("Utilisateur introuvable");
        }
        String token = UUID.randomUUID().toString();
        service.createPasswordResetTokenForUser(admin, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, admin));
        return new GenericResponse("Password reset email sent successfully");
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    public static String getAppUrl(HttpServletRequest request) {
        StringBuilder appUrl = new StringBuilder();
        appUrl.append(request.getScheme()).append("://").append(request.getServerName());
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            appUrl.append(":").append(request.getServerPort());
        }
        appUrl.append(request.getContextPath());
        return appUrl.toString();
    }
    private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, WebAdmin admin) {
        String url = "http://localhost:4200" + "/user/changePassword?token=" + token;
        String message = "To reset your password, click the link below:";
        return constructEmail("Reset Password", message + " \r\n" + url, admin);
    }

    private SimpleMailMessage constructEmail(String subject, String body, WebAdmin admin) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(admin.getEmail());
        email.setFrom("barnichahafsa@gmail.com");
        return email;
    }
    @GetMapping("/user/changePassword")
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("token") String token) {
        String result = service.validatePasswordResetToken(token);
        if (result != null) {
            String message = "OKAY";
            return "redirect:/login.html?lang=" + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }
    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(final Locale locale, PasswordDto passwordDto) {
        String result = service.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return new GenericResponse("NOO");
        }

        Optional<WebAdmin> adminOptional = service.getUserByPasswordResetToken(passwordDto.getToken());
        if (adminOptional.isPresent()) {
            WebAdmin admin = adminOptional.get();
            service.changeUserPassword(admin, passwordDto.getNewPassword());
            return new GenericResponse("ok");
        } else {
            return new GenericResponse("no");
        }
    }

}