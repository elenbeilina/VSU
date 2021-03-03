package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Prize;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.enums.TournamentLabel;
import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTournament {
    private String name;
    private String task;
    private Long sponsorId;
    private Prize prize;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private TournamentLabel label;

    public static Tournament toEntity(AddTournament from) {
        return Tournament.builder()
                .sponsorId(from.sponsorId)
                .startDate(from.startDate)
                .endDate(from.endDate)
                .status(TournamentStatus.CREATED)
                .label(from.label).build();
    }
}
