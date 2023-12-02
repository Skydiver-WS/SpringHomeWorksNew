package com.example.webappliststudents.service;


import com.example.webappliststudents.model.Student;
import com.example.webappliststudents.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentListServiceImpl implements StudentsListService {

    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public void update(Student student) {
        studentRepository.update(student);
    }
}
