package com.example.taskManagement.services;

import com.example.taskManagement.exceptions.NotFoundException;
import com.example.taskManagement.models.Task;
import com.example.taskManagement.models.User;
import com.example.taskManagement.repository.TaskRepository;
import com.example.taskManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() throws NotFoundException {
        List<Task> response = taskRepository.findAll();
        if(!response.isEmpty()){
            return response;
        }
        throw new NotFoundException("There is no tasks");

    }


    public Task createTask(String title, String description, String status, String priority, LocalDate dueDate) {

            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            return taskRepository.save(task);

    }

    public Task updateTask(String title, String description, String status, String priority, LocalDate dueDate, Long id) throws NotFoundException {
        Optional<Task> taskFromDB = taskRepository.findById(id);
        if (taskFromDB.isPresent()) {
            Task task = taskFromDB.get();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            return taskRepository.save(task);
        }
        throw new NotFoundException("Task not found");
    }
    public void deleteTask(Long id) throws NotFoundException {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new NotFoundException("Task not found with id: " + id);
        }
    }




}
