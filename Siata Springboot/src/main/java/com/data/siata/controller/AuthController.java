package com.data.siata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.data.siata.model.User;
import com.data.siata.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            String token = authService.authenticate(email, password);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token, @RequestParam String email) {
        boolean isValid = authService.isAuthorized(token, email);
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserFromToken(@RequestParam String token) {
        User user = authService.getUserFromToken(token);
        return ResponseEntity.ok(user);
    }
}
