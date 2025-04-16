package com.collegemanagement.college_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.collegemanagement.college_management.service.UserService;
import com.collegemanagement.college_management.service.EmailService;
import com.collegemanagement.college_management.model.User;
import com.collegemanagement.college_management.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Login Attempt: RollNo = " + request.getRollNo() + ", Role = " + request.getRole());

        if (request.getRollNo() == null || request.getPassword() == null || request.getRole() == null) {
            return ResponseEntity.status(400).body("Missing required fields");
        }

        User user = userService.getUserByCredentials(request.getRollNo(), request.getPassword(), request.getRole());

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials or role");
        }

        // Send email notification on successful login
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            System.out.println("Preparing to send login mail to: " + user.getEmail());
            try {
                emailService.sendLoginNotification(user.getEmail(), user.getName());
                System.out.println("Email sent successfully to: " + user.getEmail());
            } catch (Exception e) {
                System.out.println("Email sending failed to: " + user.getEmail());
                e.printStackTrace();  // full stack trace
            }
        } else {
            System.out.println("No email associated with this user.");
        }



        System.out.println("User Found: " + user.getRole());
        return ResponseEntity.ok(user);
    }
}
