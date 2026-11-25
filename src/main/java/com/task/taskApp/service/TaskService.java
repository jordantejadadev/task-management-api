
package com.task.taskApp.service;

import com.task.taskApp.dto.TaskRequestDTO;
import com.task.taskApp.dto.TaskResponseDTO;
import com.task.taskApp.entity.Task;
import com.task.taskApp.enums.TaskStatus;
import com.task.taskApp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.task.taskApp.exception.ResourceNotFoundException;
import com.task.taskApp.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponseDTO createTask(TaskRequestDTO request) {

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setResponsible(request.getResponsible());

        Task savedTask = taskRepository.save(task);

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(savedTask.getId());
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setResponsible(savedTask.getResponsible());
        response.setStatus(savedTask.getStatus());
        response.setCreatedAt(savedTask.getCreatedAt());

        return response;
    }


    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setResponsible(task.getResponsible());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());

        return response;
    }


    public List<TaskResponseDTO> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> {
                    TaskResponseDTO response = new TaskResponseDTO();

                    response.setId(task.getId());
                    response.setTitle(task.getTitle());
                    response.setDescription(task.getDescription());
                    response.setResponsible(task.getResponsible());
                    response.setStatus(task.getStatus());
                    response.setCreatedAt(task.getCreatedAt());

                    return response;
                })
                .toList();
    }


    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException("No se puede actualizar una tarea completada");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setResponsible(request.getResponsible());

        Task updatedTask = taskRepository.save(task);

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(updatedTask.getId());
        response.setTitle(updatedTask.getTitle());
        response.setDescription(updatedTask.getDescription());
        response.setResponsible(updatedTask.getResponsible());
        response.setStatus(updatedTask.getStatus());
        response.setCreatedAt(updatedTask.getCreatedAt());

        return response;
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException("No se puede eliminar una tarea completada");
        }

        taskRepository.delete(task);
    }


    public TaskResponseDTO changeTaskStatus(Long id, TaskStatus newStatus) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException("La tarea ya está completada");
        }

        if (task.getStatus() == TaskStatus.PENDING
                && newStatus != TaskStatus.IN_PROGRESS) {
            throw new BusinessException("Una tarea pendiente solo puede pasar a IN_PROGRESS");
        }

        if (task.getStatus() == TaskStatus.IN_PROGRESS
                && newStatus != TaskStatus.COMPLETED) {
            throw new BusinessException("Una tarea en progreso solo puede pasar a COMPLETED");
        }

        task.setStatus(newStatus);

        Task updatedTask = taskRepository.save(task);

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(updatedTask.getId());
        response.setTitle(updatedTask.getTitle());
        response.setDescription(updatedTask.getDescription());
        response.setResponsible(updatedTask.getResponsible());
        response.setStatus(updatedTask.getStatus());
        response.setCreatedAt(updatedTask.getCreatedAt());

        return response;
    }
}
