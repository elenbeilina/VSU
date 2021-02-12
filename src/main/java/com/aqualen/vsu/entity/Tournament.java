package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TournamentLabel;
import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tournament",schema="vsu")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tournaments")
    @SequenceGenerator(name="seq_tournaments",
            sequenceName="vsu.tournaments_seq", allocationSize=1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsor_id", nullable = false)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User sponsor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User winnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prize_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Prize prize;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private TournamentStatus status;

    @NotNull
    private TournamentLabel label;
}
