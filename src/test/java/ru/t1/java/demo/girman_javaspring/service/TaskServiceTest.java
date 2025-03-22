package ru.t1.java.demo.girman_javaspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.t1.java.demo.girman_javaspring.model.Task;
import ru.t1.java.demo.girman_javaspring.repository.TaskRepository;
import ru.t1.java.demo.girman_javaspring.service.jpa.TaskServiceImpl;
import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;
import ru.t1.java.demo.girman_javaspring.util.mapping.TaskMapper;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testGetAllTasks_WithoutSorting() {
        Task task1 = new Task(1L, "Task 1", "Description 1", null, null, TaskStatus.TODO);
        Task task2 = new Task(2L, "Task 2", "Description 2", null, null, TaskStatus.IN_PROGRESS);
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toTaskDto(task1)).thenReturn(new TaskDto(1L, "Task 1", "Description 1", null, null, TaskStatus.TODO));
        when(taskMapper.toTaskDto(task2)).thenReturn(new TaskDto(2L, "Task 2", "Description 2", null, null, TaskStatus.IN_PROGRESS));
        List<TaskDto> result = taskService.getAllTasks(null);

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(2)).toTaskDto(any(Task.class));
    }

    @Test
    public void testGetAllTasks_WithSorting() {
        String sortBy = "title";
        Task task1 = new Task(1L, "Task A", "Description A", null, null, TaskStatus.IN_PROGRESS);
        Task task2 = new Task(2L, "Task B", "Description B", null, null, TaskStatus.DONE);
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll(Sort.by(sortBy))).thenReturn(tasks);
        when(taskMapper.toTaskDto(task1)).thenReturn(new TaskDto(1L, "Task A", "Description A", null, null, TaskStatus.IN_PROGRESS));
        when(taskMapper.toTaskDto(task2)).thenReturn(new TaskDto(2L, "Task B", "Description B", null, null, TaskStatus.DONE));

        List<TaskDto> result = taskService.getAllTasks(sortBy);

        assertEquals(2, result.size());
        assertEquals("Task A", result.get(0).getTitle());
        assertEquals("Task B", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll(Sort.by(sortBy));
        verify(taskMapper, times(2)).toTaskDto(any(Task.class));
    }

    @Test
    public void testCreateTask() {
        TaskDto taskDto = new TaskDto(null, "New Task", "New Description", null, null, TaskStatus.IN_PROGRESS);
        Task task = new Task(1L, "New Task", "New Description", null, null, TaskStatus.IN_PROGRESS);

        when(taskMapper.toTask(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDto(task)).thenReturn(new TaskDto(1L, "New Task", "New Description", null, null, TaskStatus.IN_PROGRESS));

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toTask(taskDto);
        verify(taskMapper, times(1)).toTaskDto(task);
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        TaskDto updatedDto = new TaskDto(taskId, "Updated Task", "Updated Description", null, null, TaskStatus.DONE);
        Task existingTask = new Task(taskId, "Old Task", "Old Description", null, null, TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);
        when(taskMapper.toTaskDto(existingTask)).thenReturn(updatedDto);

        TaskDto result = taskService.updateTask(taskId, updatedDto);

        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.DONE, result.getStatus());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
        verify(taskMapper, times(1)).toTaskDto(existingTask);
    }

    @Test
    public void testUpdateTask_ThrowsExceptionWhenTaskNotFound() {
        Long taskId = 1L;
        TaskDto updatedDto = new TaskDto(taskId, "Updated Task", "Updated Description", null, null, TaskStatus.DONE);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.updateTask(taskId, updatedDto));
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testGetTasksByStatus() {
        TaskStatus status = TaskStatus.DONE;
        Task task1 = new Task(1L, "Task 1", "Description 1", null, null, status);
        Task task2 = new Task(2L, "Task 2", "Description 2", null, null, status);
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getTasksByStatus(status)).thenReturn(tasks);
        when(taskMapper.toTaskDto(task1)).thenReturn(new TaskDto(1L, "Task 1", "Description 1", null, null, status));
        when(taskMapper.toTaskDto(task2)).thenReturn(new TaskDto(2L, "Task 2", "Description 2", null, null, status));

        List<TaskDto> result = taskService.getTasksByStatus(status);

        assertEquals(2, result.size());
        assertEquals(status, result.get(0).getStatus());
        assertEquals(status, result.get(1).getStatus());
        verify(taskRepository, times(1)).getTasksByStatus(status);
        verify(taskMapper, times(2)).toTaskDto(any(Task.class));
    }

}
