package org.example.kursova_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.task.CreateTaskDto;
import org.example.kursova_backend.dtos.task.TaskDTO;
import org.example.kursova_backend.dtos.task.UpdateTaskDto;
import org.example.kursova_backend.services.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@CrossOrigin(origins = "http://localhost:3006")
@RequiredArgsConstructor
public class TasksController {

    private final TasksService tasksService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> create(@RequestBody CreateTaskDto createTaskDto) {
        TaskDTO createdTask = tasksService.create(createTaskDto);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> findOne(@PathVariable("id") Long id) {
        TaskDTO task = tasksService.findOne(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> update(@PathVariable("taskId") Long id, @RequestBody UpdateTaskDto updateTaskDto) {
        TaskDTO updatedTask = tasksService.update(id, updateTaskDto);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> remove(@PathVariable("taskId") Long id) {
        return tasksService.remove(id);
    }
}
