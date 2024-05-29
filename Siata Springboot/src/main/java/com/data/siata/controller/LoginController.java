package com.data.siata.controller;

import com.data.siata.dto.LoginDTO;
import com.data.siata.model.User;
import com.data.siata.service.UserService;
import com.data.siata.util.JwtUtil;
import com.data.siata.util.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
            return new LoginResponse("Please input email", false, null);
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!loginDTO.getEmail().matches(emailPattern)) {
            return new LoginResponse("Invalid email", false, null);
        }

        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return new LoginResponse("Please input password", false, null);
        }

        User user = userService.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.generateToken(user.getEmail());
                return new LoginResponse("Login Success", true, token);
            } else {
                return new LoginResponse("Wrong password", false, null);
            }
        } else {
            return new LoginResponse("Email not registered", false, null);
        }
    }
}

