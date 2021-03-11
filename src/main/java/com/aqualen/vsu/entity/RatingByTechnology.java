package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.trueSkill.Rating;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Double mean;
    private Double deviation;
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @PrePersist
    @PreUpdate
    public void generateRating(){
        if (Objects.nonNull(mean) && Objects.nonNull(deviation)) {
            setRating(new Rating(mean, deviation).getConservativeRating());
        }
    }

    @Override
    public String toString() {
        return "RatingByTechnology{" +
                "id=" + id +
                ", technology=" + technology +
                ", mean=" + mean +
                ", deviation=" + deviation +
                ", rating=" + rating +
                '}';
    }
}
