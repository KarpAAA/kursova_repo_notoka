package org.example.kursova_backend.dtos.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kursova_backend.entities.Operation;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String content;
    private String date;
    private String priority;
    private String status;
    private List<Operation> history;

}
