package com.example.demo.Controllers;

import com.example.demo.Models.User;
import com.example.demo.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/register")
public class RegisterController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            System.out.println(newUser.getEmail());
            customUserDetailsService.addUser(newUser);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }
}
