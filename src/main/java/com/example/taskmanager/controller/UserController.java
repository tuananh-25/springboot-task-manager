package com.example.taskmanager.controller;
import com.example.taskmanager.common.ApiResponse;
import com.example.taskmanager.dto.user.UserResponse;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {

        UserResponse userResponse = userService.getCurrentUser();

        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Get current user successful")
                        .data(userResponse)
                        .build();

        return ResponseEntity.ok(response);
    }
}