package com.spring.controller;

import com.spring.dto.UserResponseDTO;
import com.spring.entity.User;
import com.spring.repository.UserRepository;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            return userService.login(email, password) ? Map.of("message", "Successfully Logged") : Map.of("message", "Invalid Credentials");
        } catch (Exception e){
            return Map.of("message", e.getMessage());
        }
    }
}
