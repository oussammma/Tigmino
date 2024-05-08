package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WebAdmin implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pseudoname;
    private String passkey;
    private String nom;
    private String prenom;
    private String contact;
    private Integer connected;
    private Integer length_passkey;
    private String email;
    private String complexity_flag;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String dept_code;
    private Integer number_of_tries;
    private Integer number_of_tries_allowed;
    private String IP_address_mang;
    private String IP_address;
    private Integer nbr_session_allowed;
    private Integer number_session_connected;
    private Integer expiration_password;
    private Date date_start_passkey;
    private Date date_end_passkey;
    private String language_code;
    private String last_4_passkeys;
    private String block_access;

    public WebAdmin(String nom, String prenom, BigInteger contact, String pseudoname, String passkey, Date dateEndPasskey, Date dateStartPasskey, String blockAccess, Role role, String deptCode, Integer nbrSessionAllowed, Integer numberOfTriesAllowed) {
    }
    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private List<Token> tokens;
    @JsonIgnore
    @OneToMany(mappedBy = "syndic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppelDeFonds> appelDeFonds;
    @JsonIgnore
    @OneToMany(mappedBy = "syndic")
    private List<Residence> residences;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "admin_bloc",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "bloc_id")
    )
    private List<Bloc> managedBlocs;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
