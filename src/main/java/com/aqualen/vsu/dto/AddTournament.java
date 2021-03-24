package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Prize;
import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTournament {
    @NotNull
    private String name;
    private String task;
    @NotNull
    private Long sponsorId;
    private Prize prize;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    private List<Technology> technologies;

    public static Tournament toEntity(AddTournament from) {
        Tournament tournament = Tournament.builder()
                .name(from.name)
                .task(from.task)
                .prize(from.prize)
                .sponsorId(from.sponsorId)
                .startDate(from.startDate)
                .endDate(from.endDate)
                .status(TournamentStatus.CREATED)
                .build();
        tournament.setTechnologies(
                from.technologies.stream()
                        .peek(technology -> technology.setTournament(tournament))
                        .collect(Collectors.toList()));
        return tournament;
    }
}
