package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private jwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("=== JWT Filter START ===");
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("Method: " + request.getMethod());

        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header: " + (authHeader != null ? "Present" : "Missing"));

        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println("Token extracted: " + token.substring(0, Math.min(20, token.length())) + "...");

            try {
                username = jwtUtil.getUsername(token);
                System.out.println("Username from token: " + username);
            } catch (Exception e) {
                System.out.println("Error extracting username: " + e.getMessage());
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("User loaded: " + userDetails.getUsername());

                if(jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✓ Authentication set successfully");
                    System.out.println("✓ Authorities: " + userDetails.getAuthorities());  // FIRST DEBUG
                    System.out.println("✓ Principal: " + authToken.getPrincipal());         // FIRST DEBUG
                } else {
                    System.out.println("✗ Token validation failed");
                }
            } catch (Exception e) {
                System.out.println("✗ Error in authentication: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // SECOND DEBUG SECTION - ADD THIS
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Final Authentication before controller: " + auth);
        System.out.println("Is Authenticated: " + (auth != null && auth.isAuthenticated()));
        if (auth != null) {
            System.out.println("Final Authorities: " + auth.getAuthorities());
        }
        System.out.println("=== JWT Filter END ===\n");

        filterChain.doFilter(request, response);
    }
}
