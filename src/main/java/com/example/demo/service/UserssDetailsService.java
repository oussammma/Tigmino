package com.example.demo.service;

import com.example.demo.model.UserMob;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserssDetailsService {

    public List<UserMob> getAllUsers();
    public void deleteUserById(int id);

}
