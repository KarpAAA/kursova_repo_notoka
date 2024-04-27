package org.example.kursova_backend.dtos.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kursova_backend.entities.enums.TaskPriority;
import org.example.kursova_backend.entities.enums.TaskStatus;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateTaskDto {
    private String title;
    private String content;
    private String date;
    private TaskPriority priority;
    private TaskStatus status;
    private Long tasksListId;
}
