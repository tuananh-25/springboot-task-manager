package com.example.taskmanager.entity;
import com.example.taskmanager.entity.base.BaseEntity;
import com.example.taskmanager.entity.enums.Role;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();
}
