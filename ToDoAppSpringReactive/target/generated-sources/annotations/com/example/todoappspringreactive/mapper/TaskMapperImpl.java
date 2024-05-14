package com.example.todoappspringreactive.mapper;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.TaskResponse;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-14T23:41:43+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponse taskToTaskResponse(Task task, User user) {
        if ( task == null && user == null ) {
            return null;
        }

        TaskResponse.TaskResponseBuilder taskResponse = TaskResponse.builder();

        if ( task != null ) {
            taskResponse.id( task.getId() );
            taskResponse.name( task.getName() );
            taskResponse.description( task.getDescription() );
            taskResponse.createAt( task.getCreateAt() );
            taskResponse.updateAt( task.getUpdateAt() );
            taskResponse.status( task.getStatus() );
            Set<String> set = task.getObserverIds();
            if ( set != null ) {
                taskResponse.observerIds( new LinkedHashSet<String>( set ) );
            }
            Set<User> set1 = task.getObservers();
            if ( set1 != null ) {
                taskResponse.observers( new LinkedHashSet<User>( set1 ) );
            }
        }
        taskResponse.author( user );

        return taskResponse.build();
    }

    @Override
    public void taskToTaskRequest(Task task, String authorId) {
        if ( authorId == null ) {
            return;
        }

        task.setAuthorId( authorId );

        task.setCreateAt( java.time.Instant.now() );
    }
}
