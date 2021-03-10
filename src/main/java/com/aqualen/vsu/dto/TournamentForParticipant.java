package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentForParticipant {
    private Long tournamentId;
    private String tournamentName;
    private List<Technology> technologies;
    private String task;
    private long grade;

    public static TournamentForParticipant toDto(Participants from) {
        Tournament tournament = from.getTournament();
        return TournamentForParticipant.builder()
                .tournamentId(tournament.getId())
                .tournamentName(tournament.getName())
                .technologies(tournament.getTechnologies())
                .task(from.getTask())
                .grade(from.getGrade())
                .build();
    }
}
