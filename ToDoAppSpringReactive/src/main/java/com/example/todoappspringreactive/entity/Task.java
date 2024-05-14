package com.example.todoappspringreactive.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
@Builder
public class Task {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    @NotNull(message = "AuthorId not be null")
    private String authorId;
    private Set<String> observerIds;

    @ReadOnlyProperty
    private User author;
    @ReadOnlyProperty
    private Set<User> observers;

}
