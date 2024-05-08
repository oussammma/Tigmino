package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponseUser {
    private String token;
    private UserMob user;
    private String message ;
    private Residence residence;
    public AuthenticationResponseUser(String token,UserMob user, String message, Residence residence) {
        this.token = token;
        this.user = user;
        this.message = message;
        this.residence=residence;

    }



}
