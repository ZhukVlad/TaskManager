package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task(1L, "Test", "Some description", false);
    }

    @Test
    public void shouldGetAllTasks() {
        Mockito.when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test", tasks.get(0).getTitle());

    }

    @Test
    public void shouldGetSortedTasks() {
        Task task2 = new Task(2L, "Another Test", "Description", true);
        Mockito.when(taskRepository.findAll(Sort.by("title"))).thenReturn(List.of(task2, task));

        List<Task> tasks = taskService.getSortedTasks("title");

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Another Test", tasks.get(0).getTitle());
        assertEquals("Test", tasks.get(1).getTitle());
    }

    @Test
    public void shouldCreateTask() {
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task created = taskService.createTask(new Task());

        assertNotNull(created);
        assertEquals("Test", created.getTitle());
    }

    @Test
    public void shouldUpdateTask() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updated = taskService.updateTask(1L, new Task(null, "Updated", "Test descr", false));

        assertNotNull(updated);
        assertEquals("Updated", updated.getTitle());
    }

    @Test
    public void shouldDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void shouldToggleTask() {
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.toggleTask(1L);

        assertTrue(task.isCompleted());
        Mockito.verify(taskRepository).save(task);
    }

    @Test
    public void checkIfTaskExists() {
        Mockito.when(taskRepository.existsById(1L)).thenReturn(true);

        assertTrue(taskService.isExistsById(1L));
    }
}
