package com.example.demo.service;

import com.example.demo.model.WebAdmin;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministratorDetailsServiceImp implements UserDetailsService {
    private final WebAdminRepository repository;

    public AdministratorDetailsServiceImp(WebAdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByPseudoname(username).orElseThrow(()->new UsernameNotFoundException("Admin introuvable"));
    }

}
