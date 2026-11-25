
package com.task.taskApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String title;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

    @NotBlank(message = "El responsable es obligatorio")
    @Size(max = 100, message = "El responsable no puede superar los 100 caracteres")
    private String responsible;
}
