package com.aqualen.vsu.entity;

import com.aqualen.vsu.entity.enums.TournamentLabel;
import com.aqualen.vsu.entity.enums.TournamentStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tournament",schema="vsu")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @NotNull
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsor_id", nullable = false)
    @NotNull
    private User sponsor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id", nullable = false)
    private User winnerId;

    @NotNull
    @Column(name = "gift_picture")
    private String giftPicture;

    @NotNull
    @Column(name = "gift_name")
    private String giftName;

    @NotNull
    @Column(name = "gift_description")
    private String giftDescription;

    @NotNull
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotNull
    @Column(name = "end_date")
    private Timestamp endDate;

    @NotNull
    @Column(name = "status")
    private TournamentStatus tournamentStatus;

    @NotNull
    @Column(name = "label")
    private TournamentLabel tournamentLabel;
}
