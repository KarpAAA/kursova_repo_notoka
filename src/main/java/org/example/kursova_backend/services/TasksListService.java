package org.example.kursova_backend.services;


import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.tasks_list.CreateTasksListDto;
import org.example.kursova_backend.dtos.tasks_list.UpdateTasksListDto;
import org.example.kursova_backend.dtos.tasks_list.UpdateTasksListOrderDto;
import org.example.kursova_backend.entities.TaskBoard;
import org.example.kursova_backend.entities.TasksList;
import org.example.kursova_backend.repos.TaskBoardRepository;
import org.example.kursova_backend.repos.TasksListRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksListService {

    private final TasksListRepository tasksListRepository;
    private final TaskBoardRepository boardRepository;

    public TasksList create(CreateTasksListDto createTasksListDto) throws Exception {
        TaskBoard board = boardRepository.findById(createTasksListDto.getBoardId()).orElseThrow(() -> new Exception("Board not found"));
        TasksList tasksList =
                new TasksList(null,
                        createTasksListDto.getTitle(),
                        createTasksListDto.getNumber(),
                        List.of(),
                        board,
                        0);
        tasksList = tasksListRepository.save(tasksList);
        tasksList.setOrder_number(tasksList.getId().intValue());
        tasksListRepository.save(tasksList);
        return tasksList;
    }

    public TasksList findOne(Long id) {
        return tasksListRepository.findById(id).orElse(null); // Return null if not found
    }

    public TasksList update(Long id, UpdateTasksListDto updateTasksListDto) throws Exception {
        TasksList tasksList = tasksListRepository.findById(id).orElseThrow(() -> new Exception("Tasks list not found"));

        tasksList.setTitle(updateTasksListDto.getTitle());
        tasksList.setNumber(updateTasksListDto.getNumber());
        tasksList.setId(id);
        return tasksListRepository.save(tasksList);
    }

    public void remove(Long id) {
        tasksListRepository.deleteById(id);
    }

    public UpdateTasksListOrderDto updateOrder(UpdateTasksListOrderDto updateTasksListOrderDto) throws Exception {
        TasksList tasksListMoved = tasksListRepository
                .findById(Long.valueOf(updateTasksListOrderDto.getOrder()))
                .orElseThrow(() -> new Exception("Tasks list not found"));
        TasksList tasksListForced = tasksListRepository
                .findById(updateTasksListOrderDto.getId())
                .orElseThrow(() -> new Exception("Tasks list not found"));

        tasksListMoved.setOrder_number(tasksListForced.getOrder_number());
        tasksListForced.setOrder_number(updateTasksListOrderDto.getOrder());

        tasksListRepository.save(tasksListMoved);
        tasksListRepository.save(tasksListForced);
        return updateTasksListOrderDto;
    }
}
