package com.example.demo.DTO;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

public class Registerrequest {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;

    public Registerrequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public Registerrequest() {}
}
