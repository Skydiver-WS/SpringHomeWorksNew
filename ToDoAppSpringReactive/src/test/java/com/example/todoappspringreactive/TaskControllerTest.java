package com.example.todoappspringreactive;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.TaskStatus;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.request.ObserversRequest;
import com.example.todoappspringreactive.web.response.TaskResponse;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskControllerTest extends AbstractTest{
    @Test
    public void testFindAllTasks(){
        var expect = List.of(
                TaskResponse.builder()
                        .id(TASK_ID_1)
                        .name("Task 1")
                        .description("This is first task")
                        .status(TaskStatus.IN_PROGRESS)
                        .author(new User(USER_ID_1, "Vano", "vano@gmail.com"))
                        .build(),

                TaskResponse.builder().id(TASK_ID_2)
                        .name("Task 2")
                        .description("This is second task")
                        .status(TaskStatus.DONE)
                        .author(new User(USER_ID_2, "Petr", "petro@gmail.com"))
                        .observerIds(new HashSet<>(List.of(USER_ID_3, USER_ID_1, USER_ID_4)))
                        .observers(new HashSet<>(
                                List.of(
                                        new User(USER_ID_3, "Leva", "lev@gmail.com"),
                                        new User(USER_ID_1, "Vano", "vano@gmail.com"),
                                        new User(USER_ID_4, "Sidor", "sid@gmail.com")
                                )
                        ))
                        .build()
        );

        webClient.get()
                .uri("/task")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskResponse.class)
                .hasSize(2)
                .contains(expect.toArray(TaskResponse[]::new));
    }

    @Test
    void testFindTaskById(){
        var expect =  TaskResponse.builder().id(TASK_ID_2)
                .name("Task 2")
                .description("This is second task")
                .status(TaskStatus.DONE)
                .author(new User(USER_ID_2, "Petr", "petro@gmail.com"))
                .observerIds(new HashSet<>(List.of(USER_ID_3, USER_ID_1, USER_ID_4)))
                .observers(new HashSet<>(
                        List.of(
                                new User(USER_ID_3, "Leva", "lev@gmail.com"),
                                new User(USER_ID_1, "Vano", "vano@gmail.com"),
                                new User(USER_ID_4, "Sidor", "sid@gmail.com")
                        )
                ))
                .build();

        webClient.get()
                .uri("/task/get-task-by-id?id={id}", TASK_ID_2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskResponse.class)
                .isEqualTo(expect);

        StepVerifier.create(taskRepository.findById(TASK_ID_2))
                .expectNextCount(1L)
                .expectComplete()
                .verify();
    }

    @Test
    void testCreateTask(){
        StepVerifier.create(taskRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();

        Task task = Task.builder().id(UUID.randomUUID().toString())
                .name("Task 3")
                .description("This is first task")
                .status(TaskStatus.IN_PROGRESS)
                .authorId(USER_ID_1)
                .build();

        webClient.post()
                .uri("/task?authorId={id}", USER_ID_1)
                .body(Mono.just(task), Task.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(t -> {
                    assertNotNull(t.getId());
                    assertNotNull(t.getName());
                    assertNotNull(t.getDescription());
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    void testUpdateTask(){
        Task task = Task.builder()
                .name("Task 5")
                .description("Sorry")
                .status(TaskStatus.TODO)
                .authorId(USER_ID_1)
                .build();

        webClient.put()
                .uri("/task/update?id={id}", TASK_ID_1)
                .body(Mono.just(task), Task.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(t -> {
                    assertEquals("Task 5", t.getName());
                    assertEquals("Sorry", t.getDescription());
                    assertEquals(TaskStatus.TODO.name(), t.getStatus().name());
                });
    }

    @Test
    void testAddObservTask(){

        ObserversRequest request = ObserversRequest.builder()
                .authorId(new HashSet<>(List.of(USER_ID_3, USER_ID_1, USER_ID_4)))
                .build();

        webClient.put()
                .uri("/task/add-observ?id={id}", TASK_ID_1)
                .body(Mono.just(request), ObserversRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class)
                .value(t -> {
                   assertNotNull(t.getObserverIds());
                });
    }

    @Test
    void testDeleteTask(){
        webClient.delete()
                .uri("/task?id={id}", TASK_ID_1)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(taskRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();
    }

}
