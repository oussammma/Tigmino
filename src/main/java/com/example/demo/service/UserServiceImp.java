package com.example.demo.service;

import com.example.demo.model.UserMob;
import com.example.demo.model.WebAdmin;
import com.example.demo.repository.UserMobRepository;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserssDetailsService {
    @Autowired
    private UserMobRepository repository;


    @Override
    public List<UserMob> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        repository.deleteById(id);

    }
}
