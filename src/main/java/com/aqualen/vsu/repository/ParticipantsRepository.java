package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.enums.TournamentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, ParticipantKey> {
    List<Participants> findByTournamentId(int tournamentId);

    List<Participants> findByUserIdAndTournamentStatusNot(long userId, TournamentStatus status);
}
