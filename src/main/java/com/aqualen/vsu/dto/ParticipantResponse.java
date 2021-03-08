package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {
    private User user;
    private int grade;
    private String task;

    public static Participants toEntity(ParticipantResponse from, long tournamentId){
        return Participants.builder()
                .id(new ParticipantKey(tournamentId, from.user.getId()))
                .user(from.user)
                .tournament(Tournament.builder().id(tournamentId).build())
                .grade(from.grade)
                .task(from.task)
                .build();
    }

    public static Player toPlayer(ParticipantResponse from){
        return Player.builder()
                .user(from.user)
                .rank(from.grade).build();
    }
}
