package org.example.kursova_backend.dtos.tasks_list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTasksListOrderDto {

    private Long id;
    private Integer order;
}
