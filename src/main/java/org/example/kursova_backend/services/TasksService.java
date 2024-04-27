package org.example.kursova_backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.task.CreateTaskDto;
import org.example.kursova_backend.dtos.task.TaskDTO;
import org.example.kursova_backend.dtos.task.UpdateTaskDto;
import org.example.kursova_backend.entities.Operation;
import org.example.kursova_backend.entities.Task;
import org.example.kursova_backend.entities.TasksList;
import org.example.kursova_backend.repos.TaskRepository;
import org.example.kursova_backend.repos.TasksListRepository;
import org.example.kursova_backend.utils.DateHelpers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TaskRepository taskRepository;
    private final TasksListRepository tasksListRepository;

    public TaskDTO create(CreateTaskDto createTaskDto) throws EntityNotFoundException {
        TasksList tasksList = tasksListRepository.findById(createTaskDto.getTasksListId())
                .orElseThrow(() -> new EntityNotFoundException("TasksList with id " + createTaskDto.getTasksListId() + " not found"));

        Task newTask = new Task();

        newTask.setList(tasksList);
        newTask.setDate(createTaskDto.getDate());
        newTask.setPriority(createTaskDto.getPriority());
        newTask.setStatus(createTaskDto.getStatus());
        newTask.setHistory(createTaskDto.getHistory() !=null ? createTaskDto.getHistory(): List.of());
        newTask.setTitle(createTaskDto.getTitle());
        newTask.setContent(createTaskDto.getContent());

        Task savedTask = taskRepository.save(newTask);
        return taskToTaskDTO(savedTask);
    }

    public TaskDTO findOne(Long id) throws EntityNotFoundException {
        Task task = taskRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
        return taskToTaskDTO(task);
    }

    public TaskDTO update(Long id, UpdateTaskDto updateTaskDto) throws EntityNotFoundException {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));

        if (updateTaskDto.getTasksListId() != null
                && !Objects.equals(existingTask.getList().getId(), updateTaskDto.getTasksListId())) {
            TasksList newTaskList = tasksListRepository.findById(updateTaskDto.getTasksListId())
                    .orElseThrow(() -> new EntityNotFoundException("TasksList with id " + updateTaskDto.getTasksListId() + " not found"));

            Operation changes =
                    formOperationObject(existingTask, "moved from " + existingTask.getList().getTitle() + " to " + newTaskList.getTitle());
            existingTask.getHistory().add(changes);
            existingTask.setList(newTaskList);
        }
        else if(updateTaskDto.getTasksListId() == null){
            String editOperation = formUpdateOperation(existingTask, updateTaskDto);
            if (editOperation != null) {
                existingTask.getHistory().add(new Operation(null, editOperation, LocalDateTime.now(), existingTask));
            }
        }



        if (updateTaskDto.getTitle() != null) {
            existingTask.setTitle(updateTaskDto.getTitle());
        }
        if (updateTaskDto.getContent() != null) {
            existingTask.setContent(updateTaskDto.getContent());
        }
        if (updateTaskDto.getDate() != null) {
            existingTask.setDate(updateTaskDto.getDate());
        }
        if (updateTaskDto.getPriority() != null) {
            existingTask.setPriority(updateTaskDto.getPriority());
        }
        if (updateTaskDto.getStatus() != null) {
            existingTask.setStatus(updateTaskDto.getStatus());
        }


        Task savedTask = taskRepository.save(existingTask);
        return taskToTaskDTO(savedTask);
    }


    public ResponseEntity<?> remove(Long id) {
        taskRepository.deleteById(id);
        return null;
    }

    private TaskDTO taskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDate(DateHelpers.transformDateWithDayOfTheWeek(task.getDate()));
        taskDTO.setPriority(task.getPriority().toString());
        taskDTO.setContent(task.getContent());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setId(task.getId());
        taskDTO.setStatus(task.getStatus().toString());
        taskDTO.setHistory(task.getHistory());
        return taskDTO;
    }

    public static Operation formOperationObject(Task task, String changes) {
        String action = String.format("Task(%d) %s\n%s", task.getId(), task.getTitle(), changes);
        LocalDateTime dateTime = LocalDateTime.now();
        return new Operation(null, action, dateTime, task);
    }

    private String formUpdateOperation(Task oldTask, UpdateTaskDto updateTaskDto) {
        StringBuilder changes = new StringBuilder();

        if (!oldTask.getTitle().equals(updateTaskDto.getTitle())) {
            changes.append("title, ");
        }
        if (!oldTask.getContent().equals(updateTaskDto.getContent())) {
            changes.append("content, ");
        }
        if (!oldTask.getDate().equals(updateTaskDto.getDate())) {
            changes.append("date, ");
        }
        if (!oldTask.getPriority().equals(updateTaskDto.getPriority())) {
            changes.append("priority, ");
        }
        if (!oldTask.getStatus().equals(updateTaskDto.getStatus())) {
            changes.append("status, ");
        }

        if (!changes.isEmpty()) {
            changes.deleteCharAt(changes.length() - 1); // Видаляємо останню кому
            changes.deleteCharAt(changes.length() - 1); // Видаляємо останню кому
        }

        return !changes.isEmpty() ? "Task(" + oldTask.getId()  + ") fields that were changed: [" +changes + "]" : null;
    }



}
