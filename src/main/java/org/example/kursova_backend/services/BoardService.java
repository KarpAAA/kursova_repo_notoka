package org.example.kursova_backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.board.CreateBoardDto;
import org.example.kursova_backend.entities.TaskBoard;
import org.example.kursova_backend.entities.TasksList;
import org.example.kursova_backend.repos.TaskBoardRepository;
import org.example.kursova_backend.repos.TasksListRepository;
import org.springframework.stereotype.Service;
import org.example.kursova_backend.dtos.board.UpdateBoardDto;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final TaskBoardRepository boardRepository;
    private final TasksListRepository taskListsRepository;

    public TaskBoard create(CreateBoardDto createBoardDto) {
        TaskBoard newBoard = new TaskBoard();
        newBoard.setTitle(createBoardDto.getTitle());
        return boardRepository.save(newBoard);
    }

    public List<TaskBoard> findAll() {
         var boards = boardRepository.findAll();
         var updatedBoard = boards.stream().map((boardItem) -> {
             List<TasksList> newTaskList = boardItem.getTasksLists();
             newTaskList.sort(Comparator.comparing(TasksList::getId));

             boardItem.setTasksLists(newTaskList);
             return boardItem;
         }).toList();

        return updatedBoard;

    }

    public TaskBoard findOne(Long id) {
        Optional<TaskBoard> board = boardRepository.findById(id);
        if (board.isPresent()) {
            board.get().getTasksLists().sort(Comparator.comparingLong(TasksList::getId));
            board.get().getTasksLists().sort(Comparator.comparingInt(TasksList::getOrder_number));
        }
        return board.get();
    }

    public TaskBoard update(Long id, UpdateBoardDto updateBoardDto) throws EntityNotFoundException {
        TaskBoard board = this.findOne(id);
        if (board == null) {
            throw new EntityNotFoundException("Board with id " + id + " not found");
        }

        if (updateBoardDto.getTitle() != null) {
            board.setTitle(updateBoardDto.getTitle());
        }

        if (updateBoardDto.getNewTasksListId() != null) {
            TasksList taskList = taskListsRepository.findById(updateBoardDto.getNewTasksListId()).orElse(null);
            if (taskList != null) {
                board.getTasksLists().add(taskList);
            }
        }
        return boardRepository.save(board);
    }

    public void remove(Long id) {
        boardRepository.deleteById(id);
    }
}
