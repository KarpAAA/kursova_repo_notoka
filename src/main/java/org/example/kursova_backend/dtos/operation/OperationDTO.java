package org.example.kursova_backend.dtos.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDTO {
    private Long id;
    private String action;
    private LocalDateTime dateTime;
}
