package com.example.demo.Controllers;

import com.example.demo.DTO.Loginrequest;
import com.example.demo.DTO.Registerrequest;
import com.example.demo.Entities.user;
import com.example.demo.Services.userService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final userService userService;


    public UserController(userService userService ) {
        this.userService = userService;


    }

    //register
    @PostMapping("/register")


    public ResponseEntity<user> register(@RequestBody Registerrequest request) {
        user user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Loginrequest loginrequest) {
        String token = userService.login(loginrequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
   public ResponseEntity<user> getCurrentUser()
    {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

}
