package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class PasswordResetToken {

    public static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(targetEntity = WebAdmin.class, fetch = FetchType.EAGER)
    @JoinColumn( name = "admin_id")
    private WebAdmin admin;
    private Date expiryDate;

    public PasswordResetToken(String token, WebAdmin admin) {
        this.token=token;
        this.admin=admin;
    }


}