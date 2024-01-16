package com.example.appnews.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = {"nik_name"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String nikName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<News> list;
}
