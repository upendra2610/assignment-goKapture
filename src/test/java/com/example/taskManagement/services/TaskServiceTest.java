package com.example.taskManagement.services;

import com.example.taskManagement.dtos.CreateTaskRequestDto;
import com.example.taskManagement.exceptions.NotFoundException;
import com.example.taskManagement.models.Task;
import com.example.taskManagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {
    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService = new TaskService(taskRepository);

    @Test
    public void testGetAllTasksWhenReturnListOfTask() throws NotFoundException {
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("test task1");
        t1.setDescription("test description1");
        t1.setStatus("Todo");
        t1.setPriority("Low");
        t1.setDue_date(LocalDate.now());

        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("test task2");
        t2.setDescription("test description2");
        t2.setStatus("InProgress");
        t2.setPriority("High");
        t2.setDue_date(LocalDate.now());

        List<Task> tasks = new ArrayList<>();
        tasks.add(t1);
        tasks.add(t2);

        when(taskRepository.findAll()).thenReturn(tasks);


        List<Task> responseTest = taskService.getAllTasks();

        assertNotNull(responseTest);
        assertEquals(2,responseTest.size());
        assertEquals("test task1", responseTest.get(0).getTitle());
        assertEquals("test description1",responseTest.get(0).getDescription());
        assertEquals("Todo",responseTest.get(0).getStatus());
        assertEquals("Low",responseTest.get(0).getPriority());
        assertEquals("test task2", responseTest.get(1).getTitle());
        assertEquals("test description2",responseTest.get(1).getDescription());
        assertEquals("InProgress",responseTest.get(1).getStatus());
        assertEquals("High",responseTest.get(1).getPriority());
    }

    @Test
    public void testGetAllTaskWhenThrowsNotFoundException() {
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, ()->taskService.getAllTasks());
    }

    @Test
    public void testCreateTask(){
        String title = "title";
        String description = "description";
        String status = "Todo";
        String priority = "Low";
        LocalDate dueDate = LocalDate.now();
        CreateTaskRequestDto c = new CreateTaskRequestDto();
        c.setTitle(title);
        c.setDescription(description);
        c.setStatus(status);
        c.setPriority(priority);
        c.setDue_date(dueDate);

        Task t = new Task();
        t.setId(1L);
        t.setTitle(c.getTitle());
        t.setDescription(c.getDescription());
        t.setPriority(c.getPriority());
        t.setStatus(c.getStatus());
        t.setDue_date(c.getDue_date());
        t.setCreatedAt(LocalDate.now());

        when(taskRepository.save(any())).thenReturn(t);

        Task response = taskService.createTask(title, description, status, priority, dueDate);

        assertNotNull(response);
        assertEquals("title", response.getTitle());
        assertEquals("description",response.getDescription());
        assertEquals("Todo",response.getStatus());
        assertEquals("Low",response.getPriority());

    }

    @Test
    public void testUpdateTask() throws NotFoundException {
        Long id = 1L;
        String title = "title";
        String description = "description";
        String status = "Todo";
        String priority = "Low";
        LocalDate dueDate = LocalDate.now();
        CreateTaskRequestDto c = new CreateTaskRequestDto();
        c.setTitle(title);
        c.setDescription(description);
        c.setStatus(status);
        c.setPriority(priority);
        c.setDue_date(dueDate);

        Task t = new Task();
        t.setId(1L);
        t.setTitle(c.getTitle());
        t.setDescription(c.getDescription());
        t.setPriority(c.getPriority());
        t.setStatus(c.getStatus());
        t.setDue_date(c.getDue_date());
        t.setCreatedAt(LocalDate.now());

        when(taskRepository.save(any())).thenReturn(t);
        when(taskRepository.findById(any())).thenReturn(Optional.of(t));

        Task response = taskService.updateTask(title, description, status, priority, dueDate, id);

        assertNotNull(response);
        assertEquals("title", response.getTitle());
        assertEquals("description",response.getDescription());
        assertEquals("Todo",response.getStatus());
        assertEquals("Low",response.getPriority());
        verify(taskRepository,times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskWhenThrowsNotfoundException(){
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, ()->taskService.updateTask("title","description", "Todo", "Low",LocalDate.now(),1L));
    }

    @Test
    public void testDeleteTask() throws NotFoundException {
        when(taskRepository.existsById(any())).thenReturn(true);
        taskService.deleteTask(any());
        verify(taskRepository,times(1)).existsById(any());
        verify(taskRepository,times(1)).deleteById(any());
    }

    @Test
    public void testDeleteTaskWhenNotfoundException(){
        when(taskRepository.existsById(any())).thenReturn(false);

        assertThrows(NotFoundException.class, ()->taskService.deleteTask(any()));
    }

    @Test
    public void testFilterTasks() throws NotFoundException {
        LocalDate duedate = LocalDate.now();
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("test task1");
        t1.setDescription("test description1");
        t1.setStatus("Todo");
        t1.setPriority("High");
        t1.setDue_date(duedate);

        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("test task2");
        t2.setDescription("test description2");
        t2.setStatus("Todo");
        t2.setPriority("High");
        t2.setDue_date(duedate);

        List<Task> tasks = new ArrayList<>();
        tasks.add(t1);
        tasks.add(t2);

        when(taskRepository.filterTasks("Todo","High", duedate)).thenReturn(tasks);

        List<Task> responseTest = taskService.filterTasks("Todo","High",duedate);

        assertNotNull(responseTest);
        assertEquals(2,responseTest.size());
        assertEquals("test task1", responseTest.get(0).getTitle());
        assertEquals("test description1",responseTest.get(0).getDescription());
        assertEquals("Todo",responseTest.get(0).getStatus());
        assertEquals("High",responseTest.get(0).getPriority());
        assertEquals("test task2", responseTest.get(1).getTitle());
        assertEquals("test description2",responseTest.get(1).getDescription());
        assertEquals("Todo",responseTest.get(1).getStatus());
        assertEquals("High",responseTest.get(1).getPriority());
    }

    @Test
    public void testFilterTaskWhenThrowsNotfoundException(){
        when(taskRepository.filterTasks(any(),anyString(),any(LocalDate.class))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, ()->taskService.filterTasks(any(),anyString(),any(LocalDate.class)));
    }

    @Test
    public void testSearchTasksWhenReturnPageOfTask() throws NotFoundException {
        LocalDate duedate = LocalDate.now();
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("test task1");
        t1.setDescription("test description1");
        t1.setStatus("Todo");
        t1.setPriority("High");
        t1.setDue_date(duedate);

        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("test task2");
        t2.setDescription("test description2");
        t2.setStatus("Todo");
        t2.setPriority("High");
        t2.setDue_date(duedate);

        List<Task> tasks = new ArrayList<>();
        tasks.add(t1);
        tasks.add(t2);

        Pageable pageable = PageRequest.of(0, 2);
        PageImpl<Task> taskPage = new PageImpl<>(tasks, pageable, tasks.size());

        when(taskRepository.searchByTitleOrDescription("test",pageable)).thenReturn(taskPage);

        Page<Task> response = taskService.searchTasks("test",0,2);

        assertEquals(2, response.getTotalElements());
        assertEquals("test task1", response.getContent().get(0).getTitle());
        assertEquals("test description1", response.getContent().get(0).getDescription());
        assertEquals("Todo", response.getContent().get(0).getStatus());
        assertEquals("High", response.getContent().get(0).getPriority());


        assertEquals("test task2", response.getContent().get(1).getTitle());
        assertEquals("test description2", response.getContent().get(1).getDescription());
        assertEquals("Todo", response.getContent().get(1).getStatus());
        assertEquals("High", response.getContent().get(1).getPriority());

    }

    @Test
    public void testSearchTasksWhenThrowsNotfoundException(){
        Pageable pageable = PageRequest.of(0, 2);
        Page<Task> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(taskRepository.searchByTitleOrDescription(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        assertThrows(NotFoundException.class, () -> taskService.searchTasks("Nonexistent", 0, 2));

    }

}