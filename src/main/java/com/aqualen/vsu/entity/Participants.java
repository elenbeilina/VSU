package com.aqualen.vsu.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "participants", schema = "vsu")
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participants {
    @EmbeddedId
    private Key id;

    @ManyToOne
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private long grade;
    private String task;

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "tournament_id")
        private long tournamentId;

        @Column(name = "user_id")
        private long userId;
    }
}
