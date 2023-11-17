package com.example.liststudents.event;

import com.example.liststudents.beans.Student;

public record RemoveStudentEvent(String id, Student student) {
}
