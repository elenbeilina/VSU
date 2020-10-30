package com.aqualen.vsu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "question",schema="vsu")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_qa")
    @SequenceGenerator(name="seq_qa",
            sequenceName="vsu.question_seq", allocationSize=1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Answer answer;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "start_date")
    private Timestamp dateCreated;
}
