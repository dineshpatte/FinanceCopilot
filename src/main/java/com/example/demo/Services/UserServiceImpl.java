package com.example.demo.Services;

import com.example.demo.DTO.Loginrequest;
import com.example.demo.DTO.Registerrequest;
import com.example.demo.Entities.user;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.config.jwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements userService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtil jwtUtil;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = new jwtUtil();
    }
    @Override
    public user register(Registerrequest registerrequest) {


        if(userRepository.findByEmail(registerrequest.getEmail()).isPresent()){
            throw new UsernameNotFoundException("Email Already Exists");
        }

        user user = new user();
        user.setUsername(registerrequest.getUsername());
        user.setEmail(registerrequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerrequest.getPassword()));

        return userRepository.save(user);
    };

    @Override
    public String login(Loginrequest loginrequest) {
       if(!userRepository.findByEmail(loginrequest.getEmail()).isPresent()){
           throw new UsernameNotFoundException("Email Is Invalid");
       }

       user user = userRepository.findByEmail(loginrequest.getEmail()).get();

       if(!passwordEncoder.matches(loginrequest.getPassword(),user.getPassword())){
           throw new RuntimeException("Wrong Password");
       }
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();


       return jwtUtil.generateToken(userDetails);

    }

    @Override
    public user getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
