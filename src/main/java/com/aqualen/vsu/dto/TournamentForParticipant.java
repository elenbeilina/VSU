package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.enums.TournamentLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentForParticipant {
    private int tournamentId;
    private String tournamentName;
    private TournamentLabel label;
    private String task;
    private long grade;

    public static TournamentForParticipant toDto(Participants from) {
        Tournament tournament = from.getTournament();
        return TournamentForParticipant.builder()
                .tournamentId(tournament.getId())
                .tournamentName(tournament.getName())
                .label(tournament.getLabel())
                .task(from.getTask())
                .grade(from.getGrade())
                .build();
    }
}
