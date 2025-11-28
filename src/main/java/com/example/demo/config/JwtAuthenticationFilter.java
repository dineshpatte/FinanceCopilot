package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAuthenticationFilter {

    @Autowired
    private jwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


}
