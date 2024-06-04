package com.data.siata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.siata.model.User;
import com.data.siata.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String email, String password) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(email);
        }
        throw new RuntimeException("Invalid credentials");
    }

    public boolean isAuthorized(String token, String email) {
        return jwtUtil.validateToken(token, email);
    }

    public User getUserFromToken(String token) {
        String email = jwtUtil.extractEmail(token);
        return userService.findByEmail(email);
    }
}