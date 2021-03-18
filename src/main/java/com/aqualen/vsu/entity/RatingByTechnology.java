package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.trueSkill.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "rating", schema = "vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingByTechnology {
    @EmbeddedId
    private Key key;
    private Double mean;
    private Double deviation;
    private Long rating;

    @PrePersist
    @PreUpdate
    public void generateRating() {
        if (Objects.nonNull(mean) && Objects.nonNull(deviation)) {
            setRating(new Rating(mean, deviation).getConservativeRating());
        }
    }

    public User getUser() {
        return key.user;
    }

    public TechnologyName getTechnology() {
        return key.technology;
    }

    @Embeddable
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnore
        private User user;
        private TechnologyName technology;
    }

    @Override
    public String toString() {
        return "RatingByTechnology{" +
                ", technology=" + key.technology +
                ", mean=" + mean +
                ", deviation=" + deviation +
                ", rating=" + rating +
                '}';
    }
}
