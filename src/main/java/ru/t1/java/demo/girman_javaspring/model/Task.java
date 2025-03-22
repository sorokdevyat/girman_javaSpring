package ru.t1.java.demo.girman_javaspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private OffsetDateTime deadline;
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}