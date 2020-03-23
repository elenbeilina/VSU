package com.aqualen.vsu.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer", schema = "vsu")
@DynamicUpdate
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_answer")
    @SequenceGenerator(name = "seq_answer",
            sequenceName = "vsu.answer_seq", allocationSize = 1)
    private long id;

    private String description;
}
