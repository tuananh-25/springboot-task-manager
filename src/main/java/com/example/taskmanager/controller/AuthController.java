package com.example.taskmanager.controller;

import com.example.taskmanager.common.ApiResponse;
import com.example.taskmanager.dto.auth.RegisterRequest;
import com.example.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.taskmanager.dto.auth.AuthResponse;
import com.example.taskmanager.dto.auth.LoginRequest;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Register successful")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> response = ApiResponse
                .<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(authResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
