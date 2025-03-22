package ru.t1.java.demo.girman_javaspring.util.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.t1.java.demo.girman_javaspring.util.enums.TaskStatus;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    @JsonProperty(value = "id")
    Long id;
    @JsonProperty(value = "title")
    String title;
    @JsonProperty(value = "description")
    String description;
    @JsonProperty(value = "deadline")
    OffsetDateTime deadline;
    @JsonProperty(value = "creation_date")
    OffsetDateTime creationDate;
    @JsonProperty(value = "status")
    TaskStatus status;
}