package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    private int id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="admin_id")
    private WebAdmin admin;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserMob user;


}
