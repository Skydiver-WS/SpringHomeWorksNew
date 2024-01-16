package com.example.appnews.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @OneToOne
    private User user;
    @ManyToOne
    private News news;
}
