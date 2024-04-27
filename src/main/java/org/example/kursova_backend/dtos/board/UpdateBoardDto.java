package org.example.kursova_backend.dtos.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateBoardDto {
    private String title;
    private Long newTasksListId;
}
