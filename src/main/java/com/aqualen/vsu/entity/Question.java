package com.aqualen.vsu.entity;

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
@Table(name = "question",schema="vsu")
@DynamicUpdate
@Builder
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
    @JoinColumn(name = "answer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Answer answer;

    private String description;

    @NotNull
    private LocalDate startDate;
}
