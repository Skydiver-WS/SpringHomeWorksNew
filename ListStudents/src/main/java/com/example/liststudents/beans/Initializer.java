package com.example.liststudents.beans;

import java.util.Map;

public interface Initializer {
    Map<String, Student> loadContactsFromFile();
}