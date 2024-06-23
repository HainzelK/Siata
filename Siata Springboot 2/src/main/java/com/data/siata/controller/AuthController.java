package com.data.siata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.data.siata.dto.LoginDTO;
import com.data.siata.dto.RegisterDTO;
import com.data.siata.model.User;
import com.data.siata.service.AuthService;
import com.data.siata.util.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //pindah dari UserController
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(authService.register(registerDTO));
    }

    //pindah dari LoginController
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
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
