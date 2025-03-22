package ru.t1.java.demo.girman_javaspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.girman_javaspring.model.Task;
import ru.t1.java.demo.girman_javaspring.service.TaskService;
import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getAllTasks(@RequestParam(required = false) String sortBy) {
        return taskService.getAllTasks(sortBy);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/filter")
    public List<TaskDto> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
}
