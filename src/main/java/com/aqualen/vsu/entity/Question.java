package com.aqualen.vsu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "question",schema="vsu")
@DynamicUpdate
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_qa")
    @SequenceGenerator(name="seq_qa",
            sequenceName="vsu.question_seq", allocationSize=1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "start_date")
    private Timestamp dateCreated;
}
