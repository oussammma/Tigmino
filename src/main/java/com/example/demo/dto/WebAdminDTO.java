package com.example.demo.dto;

import com.example.demo.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
@Getter
@Setter
public class WebAdminDTO {
    private String pseudoname;

    public void setPseudoname(String pseudoname) {
        this.pseudoname = pseudoname;
    }
private String email;
    private String nom;
    private String prenom;
    private String contact;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String dept_code;
    private Integer number_of_tries_allowed;
    private Integer nbr_session_allowed;
    private Date date_start_passkey;
    private Date date_end_passkey;
    private String block_access;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public Integer getNumber_of_tries_allowed() {
        return number_of_tries_allowed;
    }

    public void setNumber_of_tries_allowed(Integer number_of_tries_allowed) {
        this.number_of_tries_allowed = number_of_tries_allowed;
    }

    public Integer getNbr_session_allowed() {
        return nbr_session_allowed;
    }

    public void setNbr_session_allowed(Integer nbr_session_allowed) {
        this.nbr_session_allowed = nbr_session_allowed;
    }

    public Date getDate_start_passkey() {
        return date_start_passkey;
    }

    public void setDate_start_passkey(Date date_start_passkey) {
        this.date_start_passkey = date_start_passkey;
    }

    public Date getDate_end_passkey() {
        return date_end_passkey;
    }

    public void setDate_end_passkey(Date date_end_passkey) {
        this.date_end_passkey = date_end_passkey;
    }

    public String getBlock_access() {
        return block_access;
    }

    public void setBlock_access(String block_access) {
        this.block_access = block_access;
    }


    public String getPseudoname() {
        return this.pseudoname;
    }
}
