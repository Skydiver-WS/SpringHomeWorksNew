package com.example.liststudents;

import com.example.liststudents.event.CreateStudentEvent;
import com.example.liststudents.event.RemoveStudentEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {
    @EventListener
    public void handleStudentCreation(CreateStudentEvent event) {
        System.out.println("New student created:\n" + event.id() + " " + event.student());
    }

    @EventListener
    public void handleStudentDeletion(RemoveStudentEvent event) {
        System.out.println("Student deleted with ID: " + event.id() + " " + event.student());
    }
}
