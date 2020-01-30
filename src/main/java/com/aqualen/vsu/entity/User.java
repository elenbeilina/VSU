package com.aqualen.vsu.entity;

import com.aqualen.vsu.entity.enums.UserRole;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users",schema="vsu")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "role_id")
    @NotNull
    private UserRole role;

    @Column(name = "student_book_id")
    @NotNull
    private long studentBookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @NotNull
    private String username;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "second_name")
    @NotNull
    private String secondName;

    private String description;

    @NotNull
    private String password;

    @NotNull
    private long rating;

    private Timestamp birthday;

    private String picture;

    @Column(name = "social_vk")
    private String linkVK;

    @Column(name = "social_fb")
    private String linkFB;

    @Column(name = "social_inst")
    private String linkINS;

    @Column(name = "social_twi")
    private String linkTWI;
}
