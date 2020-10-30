package com.aqualen.vsu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer", schema = "vsu")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_answer")
    @SequenceGenerator(name = "seq_answer",
            sequenceName = "vsu.answer_seq", allocationSize = 1)
    private long id;

    private String description;
}
