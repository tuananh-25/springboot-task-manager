package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.task.TaskRequest;
import com.example.taskmanager.dto.task.TaskResponse;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.entity.enums.TaskStatus;
import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.taskmanager.dto.task.UpdateTaskStatusRequest;
import com.example.taskmanager.dto.common.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public TaskResponse createTask(TaskRequest request) {

        User currentUser = getCurrentUser();

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.PENDING)
                .user(currentUser)
                .build();

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    @Override
    public PaginationResponse<TaskResponse> getMyTasks(
            int page,
            int size,
            String sortBy,
            String sortDirection,
            String keyword
    ) {

        User currentUser = getCurrentUser();

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage;

        if (keyword != null && !keyword.isBlank()) {

            taskPage = taskRepository
                    .findByUserAndTitleContainingIgnoreCase(
                            currentUser,
                            keyword,
                            pageable
                    );

        } else {

            taskPage = taskRepository.findByUser(
                    currentUser,
                    pageable
            );
        }

        return PaginationResponse.<TaskResponse>builder()
                .items(
                        taskPage.getContent()
                                .stream()
                                .map(this::mapToTaskResponse)
                                .toList()
                )
                .page(taskPage.getNumber())
                .size(taskPage.getSize())
                .totalElements(taskPage.getTotalElements())
                .totalPages(taskPage.getTotalPages())
                .last(taskPage.isLast())
                .build();
    }

    @Override
    public TaskResponse getTaskById(Long id) {

        User currentUser = getCurrentUser();

        Task task = getTaskByIdAndUser(id, currentUser);

        return mapToTaskResponse(task);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    private TaskResponse mapToTaskResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {

        User currentUser = getCurrentUser();

        Task task = getTaskByIdAndUser(id, currentUser);

        task.setTitle(request.getTitle());

        task.setDescription(request.getDescription());

        Task updatedTask = taskRepository.save(task);

        return mapToTaskResponse(updatedTask);
    }

    @Override
    public TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request) {

        User currentUser = getCurrentUser();

        Task task = getTaskByIdAndUser(id, currentUser);

        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        return mapToTaskResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {

        User currentUser = getCurrentUser();

        Task task = getTaskByIdAndUser(id, currentUser);

        taskRepository.delete(task);
    }

    private Task getTaskByIdAndUser(Long id, User user) {

        return taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found")
                );
    }
}