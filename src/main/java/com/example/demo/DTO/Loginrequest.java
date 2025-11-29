package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

public class Loginrequest {

    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;

    public Loginrequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Loginrequest() {}




}
