package com.example.liststudents.beans;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class StudentsInitializer implements Initializer {
    private final Map<String, Student> students = new HashMap<>();
    @Value("classpath:default-students.txt")
    private Resource contactsFile;

    @SneakyThrows
    @Override
    public Map<String, Student> loadContactsFromFile() {
        System.out.println("Active profile \"Initializer list students\"");
        byte[] bytes = FileCopyUtils.copyToByteArray(contactsFile.getInputStream());
        String[] contacts = new String(bytes, StandardCharsets.UTF_8).split("\n");
        Arrays.stream(contacts).map(c -> {
            String[] split = c.split(";");
            return new Student(split[0], split[1], split[2], Integer.parseInt(split[3]));
        }).forEach(student -> this.students.put(student.getId(), student));
        return this.students;
    }
}
