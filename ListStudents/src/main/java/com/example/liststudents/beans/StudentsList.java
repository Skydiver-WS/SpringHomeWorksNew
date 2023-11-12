package com.example.liststudents.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ShellComponent
@Component
public class StudentsList {
    private final Map<String, Student> listStudent = new HashMap<>();


    @ShellMethod("add student")
    public String add(
            @ShellOption String name,
            @ShellOption String last,
            @ShellOption Integer age) {
        UUID id = UUID.randomUUID();
        listStudent.put(id.toString(), new Student(name, last, age));
        return listStudent.containsKey(id.toString()) ? "Student added successful" : "Error. Student is not added.";
    }

    @ShellMethod("delete student")
    public String delete(@ShellOption String id) {
        listStudent.remove(id);
        return listStudent.containsKey(id) ? "Student deleted successful" : "Error. Student is not deleted.";
    }

    @ShellMethod("Clear list")
    public String clear() {
        listStudent.clear();
        return "List students clear";
    }

    @ShellMethod("list students")
    public void list() {
        listStudent.values().stream().toList().forEach(System.out::println);
    }
}
