package com.example.liststudents.beans;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return  "First name='" + firstName + "\n" +
                "Last name='" + lastName + "\n" +
                "Age=" + age + "\n";
    }
}
