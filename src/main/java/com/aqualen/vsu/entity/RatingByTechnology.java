package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.trueSkill.Rating;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @JsonBackReference
    private User user;

    @PrePersist
    @PreUpdate
    public void generateRating() {
        if (Objects.nonNull(mean) && Objects.nonNull(deviation)) {
            setRating(new Rating(mean, deviation).getConservativeRating());
        }
    }

    public TechnologyName extractTechnology() {
        return key.technology;
    }

    @Data
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "user_id")
        private Long userId;
        private TechnologyName technology;
    }

    @Override
    public String toString() {
        return "RatingByTechnology{" +
                "key=" + key +
                ", mean=" + mean +
                ", deviation=" + deviation +
                ", rating=" + rating +
                '}';
    }
}
