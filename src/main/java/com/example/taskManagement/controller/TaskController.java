package com.example.taskManagement.controller;

import com.example.taskManagement.dtos.CreateTaskRequestDto;
import com.example.taskManagement.dtos.FilterRequestDto;
import com.example.taskManagement.exceptions.NotFoundException;
import com.example.taskManagement.models.Task;
import com.example.taskManagement.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks() throws NotFoundException {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequestDto createTaskRequestDto) {
        return new ResponseEntity<>(taskService.createTask(createTaskRequestDto.getTitle(),
                createTaskRequestDto.getDescription(),
                createTaskRequestDto.getStatus(),
                createTaskRequestDto.getPriority(),
                createTaskRequestDto.getDue_date()), HttpStatus.CREATED);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody CreateTaskRequestDto createTaskRequestDto) throws NotFoundException {
        return new ResponseEntity<>(taskService.updateTask(createTaskRequestDto.getTitle(),
                createTaskRequestDto.getDescription(),
                createTaskRequestDto.getStatus(),
                createTaskRequestDto.getPriority(),
                createTaskRequestDto.getDue_date(), taskId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) throws NotFoundException {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(@RequestBody FilterRequestDto filterRequestDto) throws NotFoundException {
        return new ResponseEntity<>(taskService.filterTasks(filterRequestDto.getStatus(),
                filterRequestDto.getPriority(),
                filterRequestDto.getDue_date()), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Task>> searchTasks(@PathVariable String keyword) throws NotFoundException {
        return new ResponseEntity<>(taskService.searchTasks(keyword), HttpStatus.OK);
    }
}
