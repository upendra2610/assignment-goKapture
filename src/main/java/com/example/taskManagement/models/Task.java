package com.example.taskManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Task extends BaseModal{
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate due_date;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private User user;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
