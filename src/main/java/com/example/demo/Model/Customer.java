package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    @ManyToMany
    private Set<Roles> roles;
}
