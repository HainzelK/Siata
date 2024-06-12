package com.data.siata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import com.data.siata.model.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.data.siata.dto.LoginDTO;
import com.data.siata.dto.RegisterDTO;
import com.data.siata.model.User;
import com.data.siata.repository.TokenRepository;
import com.data.siata.repository.UserRepository;
import com.data.siata.util.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.data.siata.model.Role; // Added import for Role enum

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterDTO registerDTO) {
        if (userService.existsByUsername(registerDTO.getUsername())) {
            return new AuthResponse("Username already exists");
        }
        if (userService.existsByEmail(registerDTO.getEmail())) {
            return new AuthResponse("Email already exists");
        }
        if (userService.existsByNoTelp(registerDTO.getNoTelp())) {
            return new AuthResponse("Phone number already exists");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setFullName(registerDTO.getFullName());
        user.setGender(registerDTO.getGender());
        user.setNoTelp(registerDTO.getNoTelp());
        user.setDob(registerDTO.getDob());

        user.setRole(Role.USER); // Using the imported Role enum
        
        user = repository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthResponse("User registration was successful", accessToken, refreshToken);
    }

    public AuthResponse authenticate(LoginDTO loginDTO){
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
            return new AuthResponse("Please input email");
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!loginDTO.getEmail().matches(emailPattern)) {
            return new AuthResponse("Invalid email");
        }

        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return new AuthResponse("Please input password");
        }

        User user = userService.findByEmail(loginDTO.getEmail()); //.orElseThrow(() -> new RuntimeException("User not found")); // atau Email Not Registered
        if(user != null){
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                        )
                );
    
                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);
    
                revokeAllTokenByUser(user);
                saveUserToken(accessToken, refreshToken, user);
    
                return new AuthResponse("User login was successful", accessToken, refreshToken);
            } else {
                return new AuthResponse("Wrong password");
            }
        } else {
            return new AuthResponse("Email not registered");
        }

    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUserId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract email from token
        String email = jwtService.extractEmail(token);

        // check if the user exist in database
        User user = repository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthResponse("New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }






    public boolean isAuthorized(String token, String email) {
        return jwtService.validateToken(token, email);
    }

    public User getUserFromToken(String token) {
        String email = jwtService.extractEmail(token);
        return userService.findByEmail(email);
    }
}
