package com.example.demo.repository;

import com.example.demo.model.PasswordResetToken;
import com.example.demo.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
}
