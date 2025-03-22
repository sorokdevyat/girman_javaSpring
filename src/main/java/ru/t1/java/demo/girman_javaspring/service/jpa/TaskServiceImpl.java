package ru.t1.java.demo.girman_javaspring.service.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.girman_javaspring.model.Task;
import ru.t1.java.demo.girman_javaspring.repository.TaskRepository;
import ru.t1.java.demo.girman_javaspring.service.TaskService;
import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;
import ru.t1.java.demo.girman_javaspring.util.mapping.TaskMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDto> getAllTasks(String sortBy) {
        List<Task> allTasks;
        if (sortBy != null) {
            allTasks = taskRepository.findAll(Sort.by(sortBy));
        } else {
            allTasks = taskRepository.findAll();
        }
        return allTasks
                .stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        return taskMapper
                .toTaskDto(taskRepository.save(taskMapper.toTask(taskDto)));
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto updatedDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(updatedDto.getTitle());
        task.setDescription(updatedDto.getDescription());
        task.setCreationDate(updatedDto.getCreationDate());
        task.setDeadline(updatedDto.getDeadline());
        task.setStatus(updatedDto.getStatus());
        return taskMapper.toTaskDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.getTasksByStatus(status)
                .stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

}
