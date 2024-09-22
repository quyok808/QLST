package com.example.demo.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailRequest {
    private String name;
    private String email;
    private String message;

    // Getters and Setters
}
