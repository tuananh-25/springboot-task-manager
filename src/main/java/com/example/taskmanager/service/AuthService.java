package com.example.taskmanager.service;

import com.example.taskmanager.dto.auth.AuthResponse;
import com.example.taskmanager.dto.auth.LoginRequest;
import com.example.taskmanager.dto.auth.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
