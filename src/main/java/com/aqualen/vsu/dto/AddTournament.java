package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Prize;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TournamentLabel;
import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
    private TournamentStatus status;
    private TournamentLabel label;

    public static Tournament toEntity(AddTournament from) {
        return Tournament.builder()
                .sponsor(User.builder().id(from.sponsorId).build())
                .startDate(from.startDate)
                .endDate(from.endDate)
                .status(from.status)
                .label(from.label).build();
    }
}
