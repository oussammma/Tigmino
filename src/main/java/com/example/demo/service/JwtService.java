package com.example.demo.service;

import com.example.demo.model.UserMob;
import com.example.demo.model.WebAdmin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private String SECRET_KEY = "9f648fe567efddcc083ea5894b9ccd2fedce1b37e6df4b2427381b070f6948907d0e52fb2947bb06942387c6059129b2938d57e10ca54f5c4b9a1608478d1401b6612217725ff93313b888d29cb6f81650ee2f0fc778fdbe326bff57f440317a534226c3af907e869a2bca20bf4d7902827c9219e3b9918aaba4ec704598eab44eb46568122f1d5e35cd044f60d03d3d919661bc31aefbd66fdd9304f10f25d20908f9e4c94d25464b5d9e4ca2409ebbde8fce12d7dae9071f74c58e1fd41a73ff59051d390a7e2d44db9547da6c0205b2e1fc372ef6a6a5f796f2bfb8b5456a13202c3a944f37bfe087e1b49cec970ff8df0fb456e98f54e5543efc9d66b88f";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails admin){
        String username = extractUsername(token);
        return (username.equals(admin.getUsername())) && !isTokenExpired(token);
    }
    public boolean isValidUser(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(WebAdmin administrator){
        String token = Jwts
                .builder()
                .subject(administrator.getPseudoname())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 900000000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    public String generateTokenUser(UserMob userMob){
        String token = Jwts
                .builder()
                .subject(userMob.getPseudoname())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 900000000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }


    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

}
