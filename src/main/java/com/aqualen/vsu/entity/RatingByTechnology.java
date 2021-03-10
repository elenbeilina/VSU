package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.trueSkill.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "rating", schema = "vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingByTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TechnologyName technology;
    private Integer mean;
    private Integer deviation;
    private Double rating;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    @PreUpdate
    public void generateRating(){
        if (Objects.nonNull(mean) && Objects.nonNull(deviation)) {
            setRating(new Rating(mean, deviation).getConservativeRating());
        }
    }
}
