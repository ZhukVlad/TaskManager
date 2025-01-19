package com.example.taskManager.controller;

import com.example.taskManager.exception.InvalidTaskException;
import com.example.taskManager.model.Task;
import com.example.taskManager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/sorted")
    public List<Task> getSortedTasks(@RequestParam String attribute) {
        return taskService.getSortedTasks(attribute);
    }

    @GetMapping("/search")
    public List<Task> getSearchedTasks(@RequestParam String attribute) {
        return taskService.getSearchedTasks(attribute);
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        if (taskService.isExistsById(id)) {
            return ResponseEntity.ok(taskService.updateTask(id, task));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleTask(@PathVariable Long id) {
        if (taskService.isExistsById(id)) {
            taskService.toggleTask(id);
            return ResponseEntity.ok().build();
        } else {
            throw new InvalidTaskException("Task with id " + id + " not found");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.isExistsById(id)) {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } else {
            throw new InvalidTaskException("Task with id " + id + " not found");
        }
    }
}
