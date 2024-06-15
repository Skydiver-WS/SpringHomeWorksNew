package com.example.springappnewssecure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class News {
    @Id
    private String id;
    private String title;
    private String content;
    @ManyToOne
    private User user;

}
