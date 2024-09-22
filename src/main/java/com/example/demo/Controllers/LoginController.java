package com.example.demo.Controllers;

import com.example.demo.JWT.JwtTokenUtil;
import com.example.demo.Models.*;
import com.example.demo.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("v1/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userService; // Service to fetch user details

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest authRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // Retrieve the authenticated user
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Ensure the userDetails is an instance of CustomUserDetails
            if (userDetails instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

                // Generate JWT token using customUserDetails
                String token = jwtTokenUtil.generateToken(customUserDetails);

                // Return the token in the response
                return ResponseEntity.ok(new LoginResponse(token));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("User details not found"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid username or password"));
        } catch (Exception e) {
            // Log the detailed error message
            System.out.println("Error details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse("Error generating token"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            // Xử lý trường hợp không có người dùng nào được xác thực

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        System.out.println("Authenticated user: " + customUserDetails.getUsername());
        // Lấy thông tin người dùng từ customUserDetails
//        User user = new User();
//        user.setId(customUserDetails.getUser().getId()); // Lấy ID người dùng
//        user.setUsername(customUserDetails.getUsername());

        UserResponse user = new UserResponse(customUserDetails);
        return ResponseEntity.ok(user);
    }



}
