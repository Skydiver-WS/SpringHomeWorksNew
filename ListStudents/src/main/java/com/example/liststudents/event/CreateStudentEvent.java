package com.example.liststudents.event;

import com.example.liststudents.beans.Student;


public record CreateStudentEvent(String id ,Student student) {
}
