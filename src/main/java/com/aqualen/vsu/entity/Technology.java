package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "technology", schema = "vsu")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    @EmbeddedId
    private Key key;
    private Integer percent;

    @ManyToOne
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id")
    @JsonBackReference
    private Tournament tournament;

    @Getter
    @Embeddable
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "tournament_id")
        private long tournamentId;

        private TechnologyName technology;
    }

    public TechnologyName extractTechnology() {
        return key.technology;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "technology=" + (key == null ? null : key.technology) +
                ", percent=" + percent +
                '}';
    }
}
