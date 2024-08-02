package com.example.taskManagement.repository;

import com.example.taskManagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task save(Task task);

    List<Task> findAll();
    boolean existsTaskById(Long id);

    void deleteById(Long id);
}
