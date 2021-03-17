package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TechnologyName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Getter
    @Embeddable
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "tournament_id", insertable = false, updatable = false)
        private Tournament tournament;

        private TechnologyName technology;
    }

    public TechnologyName extractTechnology() {
        return key.technology;
    }

    public void setTournament(Tournament tournament) {
        key.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "technology=" + (key == null ? null : key.technology) +
                ", percent=" + percent +
                '}';
    }
}
