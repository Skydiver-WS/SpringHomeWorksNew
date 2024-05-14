package com.example.todoappspringreactive;

import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.UserResponse;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerTest extends AbstractTest{
    @Test
    public void testGetAllUsers() {
        var expect = List.of(
                UserResponse.builder()
                        .id(USER_ID_1)
                        .username("Vano")
                        .email("vano@gmail.com")
                        .build(),
                UserResponse.builder()
                        .id(USER_ID_2)
                        .username("Petr")
                        .email("petro@gmail.com")
                        .build(),
                UserResponse.builder()
                        .id(USER_ID_3)
                        .username("Leva")
                        .email("lev@gmail.com")
                        .build(),
                UserResponse.builder()
                        .id(USER_ID_4)
                        .username("Sidor")
                        .email("sid@gmail.com")
                        .build()
        );

        webClient.get().uri("/user")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponse.class)
                .hasSize(4)
                .contains(expect.toArray(UserResponse[]::new));
    }

    @Test
    public void testGetUserById() {
        var expect = UserResponse.builder()
                .id(USER_ID_1)
                .username("Vano")
                .email("vano@gmail.com")
                .build();

        webClient.get()
                .uri("/user/get-by-id?id={id}", USER_ID_1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .isEqualTo(expect);
    }

    @Test
    public void testCreateUser() {
        StepVerifier.create(userRepository.count())
                .expectNext(4L)
                .expectComplete()
                .verify();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("Ham")
                .email("ham@gmail.com")
                .build();

        webClient.post()
                .uri("/user")
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(response -> {
                    assertNotNull(response.getId());
                });

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();

    }

    @Test
    public void testUpdateUser() {
        User user = User.builder()
                .username("Den")
                .email("dem@ghj.er")
                .build();
        webClient.put()
                .uri("/user/update?id={id}", USER_ID_3)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectBody(User.class)
                .value(response -> {
                    assertEquals("Den", response.getUsername());
                    assertEquals("dem@ghj.er", response.getEmail());
                });
    }

    @Test
    public void testDeleteUser() {
        webClient.delete()
                .uri("/user?id={id}", USER_ID_1)
                .exchange()
                .expectStatus()
                .isNoContent();

        StepVerifier.create(userRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

}
