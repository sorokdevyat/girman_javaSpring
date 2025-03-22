package ru.t1.java.demo.girman_javaspring.util.mapping;

import org.springframework.stereotype.Component;
import ru.t1.java.demo.girman_javaspring.model.Task;
import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;

@Component
public class TaskMapper {
    public TaskDto toTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .deadline(task.getDeadline())
                .creationDate(task.getCreationDate())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public Task toTask(TaskDto taskDto) {
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .deadline(taskDto.getDeadline())
                .creationDate(taskDto.getCreationDate())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .build();
    }
}
