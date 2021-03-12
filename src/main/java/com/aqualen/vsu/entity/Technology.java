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

    @Getter
    @Embeddable
    @Builder
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        @ManyToOne
        @JoinColumn(name = "tournament_id")
        @JsonBackReference
        private Tournament tournament;
        private TechnologyName technology;
    }

    public TechnologyName getTechnology() {
        return key.technology;
    }

    public void setTournament(Tournament tournament){
        key.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "key=" + key +
                ", percent=" + percent +
                '}';
    }
}
