package com.example.taskmanager.common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;

    private String message;

    private T data;
}
