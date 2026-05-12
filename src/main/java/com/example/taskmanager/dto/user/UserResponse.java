package com.example.taskmanager.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;

    private String fullName;

    private String email;

    private String role;
}
