package com.example.webappliststudents.service;


import com.example.webappliststudents.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentsListService {
    List<Student> findAll();

    void save(Student student);

    void delete(Long id);

    void update(Student student);
}
