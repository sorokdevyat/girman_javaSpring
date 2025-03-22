package ru.t1.java.demo.girman_javaspring.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.java.demo.girman_javaspring.model.Task;
import ru.t1.java.demo.girman_javaspring.util.dtos.TaskDto;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll(Sort sort);
    List<Task> getTasksByStatus(TaskStatus status);
}
