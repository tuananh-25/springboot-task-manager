package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.auth.RegisterRequest;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.entity.enums.Role;
import com.example.taskmanager.exception.BadRequestException;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.security.jwt.JwtService;
import com.example.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.taskmanager.dto.auth.AuthResponse;
import com.example.taskmanager.dto.auth.LoginRequest;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        boolean emailExists = userRepository.existsByEmail(request.getEmail());

        if (emailExists) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (AuthenticationException exception) {

            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(request.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
