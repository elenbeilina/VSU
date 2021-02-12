package com.aqualen.vsu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "participants", schema = "vsu")
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participants {
    @JsonIgnore
    @EmbeddedId
    private ParticipantKey id;

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
}
