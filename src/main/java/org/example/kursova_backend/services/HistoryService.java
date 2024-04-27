package org.example.kursova_backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.operation.OperationDTO;
import org.example.kursova_backend.entities.Operation;
import org.example.kursova_backend.entities.Task;
import org.example.kursova_backend.entities.TaskBoard;
import org.example.kursova_backend.repos.OperationRepository;
import org.example.kursova_backend.repos.TaskBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final OperationRepository operationRepository;
    private final TaskBoardRepository boardRepository;

    public List<OperationDTO> findAll(Long boardId) throws EntityNotFoundException {
        TaskBoard board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("Board with id " + boardId + " not found"));

        List<Long> tasksIds = board.getTasksLists().stream()
                .flatMap(taskList -> taskList.getTasks().stream().map(Task::getId))
                .toList();

        List<Operation> operations = operationRepository.findByTask_IdIn(tasksIds);

        return operations.stream()
                .map(this::operationToOperationDTO)
                .toList();
    }

    private OperationDTO operationToOperationDTO(Operation operation) {
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setId(operation.getId());
        operationDTO.setAction(operation.getAction());
        operationDTO.setDateTime(operation.getDateTime());
        return operationDTO;
    }
}