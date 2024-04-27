package org.example.kursova_backend.dtos.task;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kursova_backend.entities.Operation;
import org.example.kursova_backend.entities.enums.TaskPriority;
import org.example.kursova_backend.entities.enums.TaskStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTaskDto {
    private String title;
    private String content;
    private String date;
    private TaskPriority priority;
    private TaskStatus status;
    private Long tasksListId;
    private List<Operation> history;
}
