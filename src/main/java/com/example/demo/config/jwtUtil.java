package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.userdetails.UserDetails;


import java.security.Key;
import java.util.Date;


@Configuration
public class jwtUtil {

    private final String SECRET = "secret";

    private final long EXPIRATION = 1000*60*60*10;


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getSigningKey())
                .compact();

    }


    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();


    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return(username.equals(userDetails.getUsername()));
    }
}
