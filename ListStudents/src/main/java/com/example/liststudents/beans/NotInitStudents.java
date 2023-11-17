package com.example.liststudents.beans;

import java.util.HashMap;
import java.util.Map;


public class NotInitStudents implements Initializer{
    @Override
    public Map<String, Student> loadContactsFromFile() {
        System.out.println("Active profile \"Not initializer list students\"");
        return new HashMap<>();
    }
}
