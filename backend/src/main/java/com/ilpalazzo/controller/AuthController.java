package com.ilpalazzo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restaurant.service.AuthService;
import com.restaurant.dto.LoginRequest;
import com.restaurant.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
    }
}