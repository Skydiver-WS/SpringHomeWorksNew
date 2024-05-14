package com.example.todoappspringreactive;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.TaskStatus;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.repository.TaskRepository;
import com.example.todoappspringreactive.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractTest {

    protected static String USER_ID_1 = UUID.randomUUID().toString();
    protected static String USER_ID_2 = UUID.randomUUID().toString();
    protected static String USER_ID_3 = UUID.randomUUID().toString();
    protected static String USER_ID_4 = UUID.randomUUID().toString();

    protected static String TASK_ID_1 = UUID.randomUUID().toString();
    protected static String TASK_ID_2 = UUID.randomUUID().toString();

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.8")
            .withReuse(true);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    protected WebTestClient webClient;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        userRepository.saveAll(
                List.of(
                        new User(USER_ID_1, "Vano", "vano@gmail.com"),
                        new User(USER_ID_2, "Petr", "petro@gmail.com"),
                        new User(USER_ID_3, "Leva", "lev@gmail.com"),
                        new User(USER_ID_4, "Sidor", "sid@gmail.com")
                )
        ).collectList().block();

        taskRepository.saveAll(
                        List.of(
                                Task.builder().id(TASK_ID_1)
                                        .name("Task 1")
                                        .description("This is first task")
                                        .status(TaskStatus.IN_PROGRESS)
                                        .authorId(USER_ID_1)
                                        .build(),

                                Task.builder().id(TASK_ID_2)
                                        .name("Task 2")
                                        .description("This is second task")
                                        .status(TaskStatus.DONE)
                                        .authorId(USER_ID_2)
                                        .observerIds(new HashSet<>(List.of(USER_ID_3, USER_ID_1, USER_ID_4)))
                                        .build()
                        )
                )
                .collectList().block();
    }

    @AfterEach
    void deleteAll(){
        userRepository.deleteAll().block();
        taskRepository.deleteAll().block();
    }
}
