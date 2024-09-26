package com.example.demo.Models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

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
    private Collection<? extends GrantedAuthority> roles;
    public UserResponse(CustomUserDetails customUserDetails) {
        this.id = customUserDetails.getUser().getId();
        this.username = customUserDetails.getUsername();
        this.email = customUserDetails.getUser().getEmail();
        this.firstname = customUserDetails.getUser().getFirstname();
        this.lastname = customUserDetails.getUser().getLastname();
        this.roles = customUserDetails.getAuthorities();
    }
}
