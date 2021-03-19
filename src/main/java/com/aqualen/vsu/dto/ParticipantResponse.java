package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.Technology.toTechnology;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {
    private User user;
    private int grade;
    private String task;

    public static Player toPlayer(ParticipantResponse from, Tournament tournament) {
        return Player.builder()
                .user(from.user)
                .skills(tournament.getTechnologies().stream()
                        .map(technology -> toTechnology(technology, from.user.getRatings()))
                        .collect(Collectors.toList()))
                .rank(from.grade).build();
    }
}
