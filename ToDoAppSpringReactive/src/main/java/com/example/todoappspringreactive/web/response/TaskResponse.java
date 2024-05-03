package com.example.todoappspringreactive.web.response;

import com.example.todoappspringreactive.entity.TaskStatus;
import com.example.todoappspringreactive.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
@Getter
@Setter
public class TaskResponse {
    private String name;
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    private Set<String> observerlds;

    private User author;
    private Set<User> observers;
}
