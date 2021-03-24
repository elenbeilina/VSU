package com.aqualen.vsu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "question_id")
    private Long id;

    private String description;

    @OneToOne
    @MapsId
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
