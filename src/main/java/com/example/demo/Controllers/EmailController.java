package com.example.demo.Controllers;

import com.example.demo.Models.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/api/send-email")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nva2572003@gmail.com"); // Email nhận
        message.setSubject("New message from " + emailRequest.getName() + "(P's Market)");
        message.setText(emailRequest.getMessage() + "\n\nFrom: " + emailRequest.getEmail());

        mailSender.send(message);
        return "Email sent successfully!";
    }
}
