package com.example.demo.api.v1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
