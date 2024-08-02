package com.example.taskManagement.repository;

import com.example.taskManagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task save(Task task);

    List<Task> findAll();
    boolean existsById(Long id);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM task t WHERE t.title LIKE %:keyword% OR t.description LIKE %:keyword%", nativeQuery = true)
    List<Task> searchByTitleOrDescription(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:due_date IS NULL OR t.due_date = :due_date)", nativeQuery = true)
    List<Task> filterTasks(@Param("status") String status,
                           @Param("priority") String priority,
                           @Param("due_date") LocalDate due_date);
}
