package com.example.webappliststudents.repository;


import com.example.webappliststudents.model.Student;
import com.example.webappliststudents.model.StudentRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@org.springframework.stereotype.Repository
@Slf4j
@RequiredArgsConstructor
@Primary
public class StudentRepository implements Repository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> findAll() {
        log.debug("{} - View list student", StudentRepository.class.getName());
        String sql = "SELECT * FROM list_students.students";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    @Override
    public void save(Student student) {
        student.setId(System.currentTimeMillis());
        String sql = "INSERT INTO list_students.students (id, first_name, last_name, email, tell_number) VALUES (?, ?, ?, ? , ?)";
        jdbcTemplate.update(sql, student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getTellNumber());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM list_students.students WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Student student) {

    }

    private Student findById(Long id) {
        log.debug("Calling DatabaseTaskRepository->findById with ID: {}", id);
        String sql = "SELECT * FROM list_students.students WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
    }
}
