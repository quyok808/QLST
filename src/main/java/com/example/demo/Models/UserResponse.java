package com.example.demo.Models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    public UserResponse(CustomUserDetails customUserDetails) {
        this.id = customUserDetails.getUser().getId();
        this.username = customUserDetails.getUsername();
        this.email = customUserDetails.getUser().getEmail();
        this.firstname = customUserDetails.getUser().getFirstname();
        this.lastname = customUserDetails.getUser().getLastname();
    }
}
