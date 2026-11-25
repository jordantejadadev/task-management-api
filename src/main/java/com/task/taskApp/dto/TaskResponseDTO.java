package com.task.taskApp.dto;

import com.task.taskApp.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String responsible;
    private TaskStatus status;
    private LocalDateTime createdAt;
}
