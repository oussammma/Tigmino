package com.example.demo.filter;

import com.example.demo.repository.TokenRepository;
import com.example.demo.service.AdministratorDetailsServiceImp;
import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AdministratorDetailsServiceImp adminDetailsService;
    private final TokenRepository tokenRepo;

    public JwtAuthenticationFilter(JwtService jwtService, AdministratorDetailsServiceImp adminDetailsService, TokenRepository tokenRepo) {
        this.jwtService = jwtService;
        this.adminDetailsService = adminDetailsService;
        this.tokenRepo = tokenRepo;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring( 7);
        String username = jwtService.extractUsername(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails adminDetails  = adminDetailsService.loadUserByUsername(username);
            var isTokenValid = tokenRepo.findByToken(token)
                    .map(t-> !t.isExpired()&& !t.isRevoked())
                    .orElse(false);
            if(jwtService.isValid(token, adminDetails) && isTokenValid){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        adminDetails, null, adminDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}