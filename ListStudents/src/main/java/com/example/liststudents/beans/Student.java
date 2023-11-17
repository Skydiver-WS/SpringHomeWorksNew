package com.example.liststudents.beans;

import lombok.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return String.format("""
                Id: %s;
                First name: %s;
                Last name: %s;
                Age: %d.""", id, firstName, lastName, age);
    }
}
