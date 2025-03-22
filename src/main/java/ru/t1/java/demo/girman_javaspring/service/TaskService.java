package ru.t1.java.demo.girman_javaspring.service;

import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks(String sortBy);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
    List<TaskDto> getTasksByStatus(TaskStatus status);
}
