package com.aqualen.vsu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "news",schema="vsu")
@DynamicUpdate
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( updatable = false )
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "date_created")
    private Timestamp dateCreated;
}
