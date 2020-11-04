package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "users",schema="vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name="seq_user",
            sequenceName="vsu.user_seq", allocationSize=1)
    private long id;

    @Column(name = "role_id")
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserRole role;

    @Column(name = "student_book_id")
    @NotNull
    private String studentBookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    private String password;

    @NotNull
    private long rating;

    private LocalDate birthday;

    private String picture;

    private String vk;
    private String facebook;
    private String instagram;
    private String twitter;
}
