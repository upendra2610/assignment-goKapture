package com.example.taskManagement.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTaskRequestDto {
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate due_date;
}
