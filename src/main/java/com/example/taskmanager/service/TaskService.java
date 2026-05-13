package com.example.taskmanager.service;

import com.example.taskmanager.dto.task.TaskRequest;
import com.example.taskmanager.dto.task.TaskResponse;
import com.example.taskmanager.dto.task.UpdateTaskStatusRequest;
import com.example.taskmanager.dto.common.PaginationResponse;
import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    PaginationResponse<TaskResponse> getMyTasks(
            int page,
            int size,
            String sortBy,
            String sortDirection,
            String keyword
    );

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest request);

    TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request);

    void deleteTask(Long id);
}