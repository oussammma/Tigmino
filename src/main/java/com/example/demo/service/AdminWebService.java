package com.example.demo.service;

import com.example.demo.model.WebAdmin;
import com.example.demo.repository.WebAdminRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminWebService {

    public List<WebAdmin> getAllAdmins();
    public void deleteAdminById(int id);

}
