package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "users", schema = "vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name = "seq_user",
            sequenceName = "vsu.users_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "role_id")
    private UserRole role;

    private String studentBookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    private String username;
    private String firstName;
    private String secondName;
    private String description;
    private String password;

    private LocalDate birthday;
    private String picture;
    private String vk;
    private String facebook;
    private String instagram;
    private String twitter;

    @OneToMany(mappedBy = "key.user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingByTechnology> ratings;

    public void addRating(RatingByTechnology rating){
        ratings.add(rating);
    }
}
