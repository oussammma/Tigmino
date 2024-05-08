package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private WebAdmin admin;

    private String message ;
    public AuthenticationResponse(String token,WebAdmin admin, String message) {
        this.token = token;
        this.admin = admin;
        this.message = message;

    }



}
