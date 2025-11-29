package com.example.demo.Services;

import com.example.demo.DTO.Loginrequest;
import com.example.demo.DTO.Registerrequest;
import com.example.demo.Entities.user;

public interface
userService {
    user register(Registerrequest registerrequest);
    String login (Loginrequest loginrequest);
    user getCurrentUser();
}
