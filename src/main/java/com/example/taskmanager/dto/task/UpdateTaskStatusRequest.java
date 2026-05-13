package com.example.taskmanager.dto.task;
import com.example.taskmanager.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusRequest {
    @NotNull(message = "Status is required")
    private TaskStatus status;
}

