package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.Prize;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TournamentLabel;
import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AddTournament {
    Long sponsorId;
    Prize prize;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime endDate;
    TournamentStatus status;
    TournamentLabel label;

    public static Tournament toEntity(AddTournament from) {
        return Tournament.builder()
                .sponsor(User.builder().id(from.sponsorId).build())
                .startDate(from.startDate)
                .endDate(from.endDate)
                .status(from.status)
                .label(from.label).build();
    }
}
