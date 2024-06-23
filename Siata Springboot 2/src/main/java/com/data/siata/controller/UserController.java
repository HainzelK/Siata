package com.data.siata.controller;

import com.data.siata.dto.UserDTO;
import com.data.siata.dto.UserProfileDTO;
import com.data.siata.model.User;
import com.data.siata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestParam String email) {
        Integer userId = userService.findByEmail(email).getUserId();
        return ResponseEntity.ok(userId);
    }

    //pindahke AuthController>AuthService
    // @PostMapping("/register")
    // public Message registerUser(@RequestBody RegisterDTO registerDTO) {
    //     if (userService.existsByUsername(registerDTO.getUsername())) {
    //         return new Message("Username already exists", false);
    //     }
    //     if (userService.existsByEmail(registerDTO.getEmail())) {
    //         return new Message("Email already exists", false);
    //     }
    //     if (userService.existsByNoTelp(registerDTO.getNoTelp())) {
    //         return new Message("Phone number already exists", false);
    //     }

    //     User user = new User();
    //     user.setUsername(registerDTO.getUsername());
    //     user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    //     user.setEmail(registerDTO.getEmail());
    //     user.setFullName(registerDTO.getFullName());
    //     user.setGender(registerDTO.getGender());
    //     user.setNoTelp(registerDTO.getNoTelp());
    //     user.setDob(registerDTO.getDob());
        
    //     userService.saveUser(user);
    //     return new Message("User registered successfully", true);
    // }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        byte[] decodedBytes = Base64.getDecoder().decode(userDTO.getProfilePic().split(",")[1]);
        user.setProfilePic(decodedBytes);
        user.setGender(userDTO.getGender());
        user.setNoTelp(userDTO.getNoTelp());
        userService.saveUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UserDTO userDetails) {
        return userService.getUserById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setFullName(userDetails.getFullName());
                    byte[] decodedBytes = Base64.getDecoder().decode(userDetails.getProfilePic().split(",")[1]);
                    user.setProfilePic(decodedBytes);
                    user.setGender(userDetails.getGender());
                    user.setNoTelp(userDetails.getNoTelp());
                    User updatedUser = userService.saveUser(user);
                    return ResponseEntity.ok(updatedUser);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/changepp/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UserProfileDTO userDetails) {
        return userService.getUserById(id)
                .map(user -> {
                    byte[] decodedBytes = Base64.getDecoder().decode(userDetails.getProfilePic().split(",")[1]);
                    user.setProfilePic(decodedBytes);
                    User updatedUser = userService.saveUser(user);
                    return ResponseEntity.ok(updatedUser);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
