package com.aqualen.vsu.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "req_question",schema="vsu")
public class RequestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "start_date")
    private Timestamp dateCreated;
}
