package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users",schema="vsu")
@DynamicUpdate
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
    private UserRole role;

    @Column(name = "student_book_id")
    @NotNull
    private String studentBookId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "social_id", nullable = false)
    private SocialMedia socialMedia;
}
