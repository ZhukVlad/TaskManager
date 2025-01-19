package com.example.taskManager.service;

import com.example.taskManager.exception.InvalidTaskException;
import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getSortedTasks(String attribute) {
        return taskRepository.findAll(Sort.by(attribute));
    }

    public List<Task> getSearchedTasks(String attribute) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getTitle().toLowerCase().contains(attribute.toLowerCase())
                        || task.getDescription().toLowerCase().contains(attribute.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Task createTask(Task task) {
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new InvalidTaskException("Invalid task id"));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(task);
    }

    public void toggleTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new InvalidTaskException("Invalid task id"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean isExistsById(Long id) {
        return taskRepository.existsById(id);
    }
}
