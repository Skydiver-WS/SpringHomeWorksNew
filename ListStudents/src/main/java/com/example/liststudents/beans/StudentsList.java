package com.example.liststudents.beans;

import com.example.liststudents.event.CreateStudentEvent;
import com.example.liststudents.event.RemoveStudentEvent;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ShellComponent
@Service
@Getter
public class StudentsList {
    private final Map<String, Student> listStudent = new HashMap<>();
    private final ApplicationEventPublisher publisher;
    private final Initializer initializer;

    @Autowired
    public StudentsList(ApplicationEventPublisher publisher, Initializer initializer) {
        this.publisher = publisher;
        this.initializer = initializer;
        listStudent.putAll(initializer.loadContactsFromFile());

    }

    @ShellMethod("add student")
    public String add(
            @ShellOption(value = "fn") String first,
            @ShellOption(value = "ln") String last,
            @ShellOption(value = "a") Integer age) {
        UUID id = UUID.randomUUID();
        Student student = new Student(id.toString(), first, last, age);
        listStudent.put(id.toString(), student);
        publisher.publishEvent(new CreateStudentEvent(id.toString(), student));
        return listStudent.containsKey(id.toString()) ? "Student added successful" : "Error. Student is not added.";
    }

    @ShellMethod(key = "r", value = "remove student")
    public String remove(@ShellOption String id) {
        Student student = listStudent.remove(id.trim());
        if (student != null) {
           publisher.publishEvent(new RemoveStudentEvent(id, student));
            return "Student deleted successfully";
        } else {
            return "Error. Student is not deleted.";
        }
    }

    @ShellMethod(key = "rm", value = "Clear list")
    public String clear() {
        listStudent.clear();
        return "List students clear";
    }

    @ShellMethod(key = "ls", value = "list students")
    public void list() {
        listStudent.values().stream().toList().forEach(System.out::println);
    }
    @ShellMethod
    public void exit(){
        System.exit(0);
    }
}
