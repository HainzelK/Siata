package com.data.siata.controller;

import com.data.siata.dto.UserDTO;
import com.data.siata.model.User;
import com.data.siata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setProfilePic(userDTO.getProfilePic());
        user.setGender(userDTO.getGender());
        user.setNoTelp(userDTO.getNoTelp());
        userService.saveUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        return userService.getUserById(id)
            .map(user -> {
                user.setUsername(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                user.setEmail(userDetails.getEmail());
                user.setFullName(userDetails.getFullName());
                user.setProfilePic(userDetails.getProfilePic());
                user.setGender(userDetails.getGender());
                user.setNoTelp(userDetails.getNoTelp());
                User updatedUser = userService.saveUser(user);
                return ResponseEntity.ok(updatedUser);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
