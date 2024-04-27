package org.example.kursova_backend.dtos.tasks_list;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTasksListDto {
    private String title;
    private Integer number;
    private Long boardId;
}
