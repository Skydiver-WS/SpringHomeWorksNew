package com.example.webappliststudents.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String tellNumber;

}
