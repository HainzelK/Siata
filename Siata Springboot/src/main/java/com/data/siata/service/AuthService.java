package com.data.siata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

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

    // public AuthResponse register(RegisterDTO registerDTO) {
    //     if (userService.existsByUsername(registerDTO.getUsername())) {
    //         return new AuthResponse("Username already exists");
    //     }
    //     if (userService.existsByEmail(registerDTO.getEmail())) {
    //         return new AuthResponse("Email already exists");
    //     }
    //     if (userService.existsByNoTelp(registerDTO.getNoTelp())) {
    //         return new AuthResponse("Phone number already exists");
    //     }

    //     User user = new User();
    //     user.setUsername(registerDTO.getUsername());
    //     user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    //     user.setEmail(registerDTO.getEmail());
    //     user.setFullName(registerDTO.getFullName());
    //     user.setGender(registerDTO.getGender());
    //     user.setNoTelp(registerDTO.getNoTelp());
    //     user.setDob(registerDTO.getDob());

    //     user.setRole(Role.USER); // Using the imported Role enum
        
    //     user = repository.save(user);

    //     String accessToken = jwtService.generateAccessToken(user);
    //     String refreshToken = jwtService.generateRefreshToken(user);

    //     saveUserToken(accessToken, refreshToken, user);

    //     return new AuthResponse("User registration was successful", accessToken, refreshToken);
    // }

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


public AuthResponse register(RegisterDTO registerDTO) {
    // Validate password length
    if (registerDTO.getPassword().length() < 8) {
        return new AuthResponse("Password must be at least 8 characters long");
    }

    // Validate email format
    String emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    if (!Pattern.matches(emailPattern, registerDTO.getEmail())) {
        return new AuthResponse("Invalid email format");
    }

    // Validate full name (no numbers or special characters)
    String fullNamePattern = "^[a-zA-Z\\s]+$";
    if (!Pattern.matches(fullNamePattern, registerDTO.getFullName())) {
        return new AuthResponse("Full name cannot contain numbers or special characters");
    }

    // Validate gender
    if (!registerDTO.getGender().equalsIgnoreCase("Male") && 
        !registerDTO.getGender().equalsIgnoreCase("Female")) {
        return new AuthResponse("Gender must be either 'Male' or 'Female'");
    }

    // Validate and sanitize phone number
    String phonePattern = "^[0-9]{9,12}$";
    String sanitizedPhoneNumber = registerDTO.getNoTelp().startsWith("+62") 
                                  ? "0" + registerDTO.getNoTelp().substring(3) 
                                  : registerDTO.getNoTelp();
    if (!Pattern.matches(phonePattern, sanitizedPhoneNumber)) {
        return new AuthResponse("Phone number must be between 9 and 12 digits long");
    }

    // Validate date of birth
    LocalDate today = LocalDate.now();
    LocalDate dob;
    try {
        dob = LocalDate.parse(registerDTO.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (dob.isAfter(today)) {
            return new AuthResponse("Date of birth cannot be in the future");
        }
    } catch (DateTimeParseException e) {
        return new AuthResponse("Date of birth must be in the format YYYY-MM-DD");
    }

    // Convert LocalDate to String
    String dobString = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    // Check for existing username, email, or phone number
    if (userService.existsByUsername(registerDTO.getUsername())) {
        return new AuthResponse("Username already exists");
    }
    if (userService.existsByEmail(registerDTO.getEmail())) {
        return new AuthResponse("Email already exists");
    }
    if (userService.existsByNoTelp(sanitizedPhoneNumber)) {
        return new AuthResponse("Phone number already exists");
    }

    // Create and save the new user
    User user = new User();
    user.setUsername(registerDTO.getUsername());
    user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    user.setEmail(registerDTO.getEmail());
    user.setFullName(registerDTO.getFullName());
    user.setGender(registerDTO.getGender());
    user.setNoTelp(sanitizedPhoneNumber);
    user.setDob(dobString); // Set dob as String
    user.setRole(Role.USER);
    try {
        byte[] decodedBytes = Base64.getDecoder().decode("/9j/4AAQSkZJRgABAQEBLAEsAAD/4QBWRXhpZgAATU0AKgAAAAgABAEaAAUAAAABAAAAPgEbAAUAAAABAAAARgEoAAMAAAABAAIAAAITAAMAAAABAAEAAAAAAAAAAAEsAAAAAQAAASwAAAAB/+0ALFBob3Rvc2hvcCAzLjAAOEJJTQQEAAAAAAAPHAFaAAMbJUccAQAAAgAEAP/hDW5odHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvADw/eHBhY2tldCBiZWdpbj0n77u/JyBpZD0nVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkJz8+Cjx4OnhtcG1ldGEgeG1sbnM6eD0nYWRvYmU6bnM6bWV0YS8nIHg6eG1wdGs9J0ltYWdlOjpFeGlmVG9vbCAxMS44OCc+CjxyZGY6UkRGIHhtbG5zOnJkZj0naHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyc+CgogPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9JycKICB4bWxuczp0aWZmPSdodHRwOi8vbnMuYWRvYmUuY29tL3RpZmYvMS4wLyc+CiAgPHRpZmY6UmVzb2x1dGlvblVuaXQ+MjwvdGlmZjpSZXNvbHV0aW9uVW5pdD4KICA8dGlmZjpYUmVzb2x1dGlvbj4zMDAvMTwvdGlmZjpYUmVzb2x1dGlvbj4KICA8dGlmZjpZUmVzb2x1dGlvbj4zMDAvMTwvdGlmZjpZUmVzb2x1dGlvbj4KIDwvcmRmOkRlc2NyaXB0aW9uPgoKIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PScnCiAgeG1sbnM6eG1wPSdodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvJz4KICA8eG1wOkNyZWF0b3JUb29sPkFkb2JlIFN0b2NrIFBsYXRmb3JtPC94bXA6Q3JlYXRvclRvb2w+CiA8L3JkZjpEZXNjcmlwdGlvbj4KCiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0nJwogIHhtbG5zOnhtcE1NPSdodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vJz4KICA8eG1wTU06RG9jdW1lbnRJRD54bXAuaWlkOjBjZmI0YmE2LWY3OWUtNDY2MS04YThmLWM3NzMxNjk2NjQ2YzwveG1wTU06RG9jdW1lbnRJRD4KICA8eG1wTU06SW5zdGFuY2VJRD5hZG9iZTpkb2NpZDpzdG9jazozNTcxODA3Mi05MDkyLTRmODgtYmUzMi1mZjkwZDdlNzQxZTI8L3htcE1NOkluc3RhbmNlSUQ+CiAgPHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD5hZG9iZTpkb2NpZDpzdG9jazo1MTYyNzU4MDE8L3htcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD4KIDwvcmRmOkRlc2NyaXB0aW9uPgo8L3JkZjpSREY+CjwveDp4bXBtZXRhPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAo8P3hwYWNrZXQgZW5kPSd3Jz8+/9sAQwAF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");
        user.setProfilePic(decodedBytes);
    } catch (IllegalArgumentException e) {
        // Handle decoding error
        e.printStackTrace(); // Or log the error
        // Optionally set a default profile picture or handle the error appropriately
    }
    user = repository.save(user);

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    saveUserToken(accessToken, refreshToken, user);

    return new AuthResponse("User registration was successful", accessToken, refreshToken);
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
