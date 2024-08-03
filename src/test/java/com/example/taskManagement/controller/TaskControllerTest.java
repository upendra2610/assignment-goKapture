package com.example.taskManagement.controller;

import com.example.taskManagement.dtos.CreateTaskRequestDto;
import com.example.taskManagement.models.Task;
import com.example.taskManagement.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTasksReturnsEmptyListWhenListOfTask() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskService.getAllTasks())
                .thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    public void testCreateTask() throws Exception {
        CreateTaskRequestDto dto = new CreateTaskRequestDto();
        Task task = new Task();
        when(taskService.createTask(anyString(), anyString(), anyString(), anyString(), any(LocalDate.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateTask() throws Exception {
        CreateTaskRequestDto dto = new CreateTaskRequestDto();
        Task task = new Task();
        when(taskService.updateTask(anyString(), anyString(), anyString(), anyString(), any(LocalDate.class), anyLong())).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }




}