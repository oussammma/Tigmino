package com.example.demo.service;

import com.example.demo.model.WebAdmin;
import com.example.demo.repository.UserMobRepository;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserMobRepository repository;

    public UserDetailsServiceImp(UserMobRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByPseudoname(username).orElseThrow(()->new UsernameNotFoundException("Utilisateur introuvable"));
    }

}
