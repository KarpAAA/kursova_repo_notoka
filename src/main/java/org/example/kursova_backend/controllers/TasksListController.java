package org.example.kursova_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.kursova_backend.dtos.tasks_list.CreateTasksListDto;
import org.example.kursova_backend.dtos.tasks_list.UpdateTasksListDto;
import org.example.kursova_backend.dtos.tasks_list.UpdateTasksListOrderDto;
import org.example.kursova_backend.entities.TasksList;
import org.example.kursova_backend.services.TasksListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3006")
@RequiredArgsConstructor
public class TasksListController {

    private final TasksListService tasksListService;


    @PostMapping("/tasks-list")
    public ResponseEntity<TasksList> create(@RequestBody CreateTasksListDto createTasksListDto) throws Exception {
        TasksList createdTaskList = tasksListService.create(createTasksListDto);
        return ResponseEntity.ok(createdTaskList); // Return created resource with status code 201
    }

    @PatchMapping("/tasks-list/{id}")
    public ResponseEntity<TasksList> update(@PathVariable("id") Long id, @RequestBody UpdateTasksListDto updateTasksListDto) throws Exception {
        TasksList updatedTaskList = tasksListService.update(id, updateTasksListDto);
        return ResponseEntity.ok(updatedTaskList); // Return updated resource with status code 200
    }

    @PatchMapping("/tasks-list/order/{id}") // Separate path for update order
    public ResponseEntity<UpdateTasksListOrderDto> updateOrder(@PathVariable("id") Long id, @RequestBody UpdateTasksListOrderDto updateTasksListDto) throws Exception {
        updateTasksListDto.setId(id); // Set the id from path variable
        UpdateTasksListOrderDto updatedTaskListOrder = tasksListService.updateOrder(updateTasksListDto);
        return ResponseEntity.ok(updatedTaskListOrder); // Return updated order with status code 200
    }

    @DeleteMapping("/tasks-list/{id}") // Delete by id
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        tasksListService.remove(id);
        return ResponseEntity.noContent().build(); // Return status code 204 (No Content)
    }
}
