package com.example.taskmanager.controller;

import com.example.taskmanager.common.ApiResponse;
import com.example.taskmanager.dto.task.TaskRequest;
import com.example.taskmanager.dto.task.TaskResponse;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanager.dto.task.UpdateTaskStatusRequest;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskRequest request
    ) {

        TaskResponse taskResponse = taskService.createTask(request);

        ApiResponse<TaskResponse> response =
                ApiResponse.<TaskResponse>builder()
                        .success(true)
                        .message("Create task successful")
                        .data(taskResponse)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getMyTasks() {

        List<TaskResponse> tasks = taskService.getMyTasks();

        ApiResponse<List<TaskResponse>> response =
                ApiResponse.<List<TaskResponse>>builder()
                        .success(true)
                        .message("Get tasks successful")
                        .data(tasks)
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long id) {

        TaskResponse taskResponse = taskService.getTaskById(id);

        ApiResponse<TaskResponse> response =
                ApiResponse.<TaskResponse>builder()
                        .success(true)
                        .message("Get task successful")
                        .data(taskResponse)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {

        TaskResponse taskResponse = taskService.updateTask(id, request);

        ApiResponse<TaskResponse> response =
                ApiResponse.<TaskResponse>builder()
                        .success(true)
                        .message("Update task successful")
                        .data(taskResponse)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {

        TaskResponse taskResponse = taskService.updateTaskStatus(id, request);

        ApiResponse<TaskResponse> response =
                ApiResponse.<TaskResponse>builder()
                        .success(true)
                        .message("Update task status successful")
                        .data(taskResponse)
                        .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Delete task successful")
                        .data(null)
                        .build();

        return ResponseEntity.ok(response);
    }
}
