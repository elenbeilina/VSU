package com.aqualen.vsu.entity;

import com.aqualen.vsu.enums.TournamentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "tournament", schema = "vsu")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tournaments")
    @SequenceGenerator(name = "seq_tournaments",
            sequenceName = "vsu.tournament_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String task;

    @Column(name = "sponsor_id")
    private long sponsorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User winnerId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prize_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Prize prize;

    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull
    private TournamentStatus status;

    @OneToMany(mappedBy = "tournament", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Technology> technologies;
}
