package com.aqualen.vsu.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantKey implements Serializable {
    @Column(name = "tournament_id")
    private int tournamentId;

    @Column(name = "user_id")
    private long userId;
}
