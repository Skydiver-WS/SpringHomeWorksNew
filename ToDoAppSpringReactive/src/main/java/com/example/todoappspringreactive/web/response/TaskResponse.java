package com.example.todoappspringreactive.web.response;

import com.example.todoappspringreactive.entity.TaskStatus;
import com.example.todoappspringreactive.entity.User;
import lombok.*;

import java.time.Instant;
import java.util.Set;
@Data
@Builder
public class TaskResponse {
    private String  id;
    private String name;
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    private Set<String> observerIds;

    private User author;
    private Set<User> observers;
}
