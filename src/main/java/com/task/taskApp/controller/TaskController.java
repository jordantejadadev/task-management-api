
package com.task.taskApp.controller;

import com.task.taskApp.dto.TaskRequestDTO;
import com.task.taskApp.dto.TaskResponseDTO;
import com.task.taskApp.enums.TaskStatus;
import com.task.taskApp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO request) {
        return taskService.createTask(request);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/status")
    public TaskResponseDTO changeTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {
        return taskService.changeTaskStatus(id, status);
    }

}
