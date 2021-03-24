package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.enums.TournamentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Participants.Key> {
    List<Participants> findByTournamentId(long tournamentId);

    List<Participants> findByUserIdAndTournamentStatusNotIn(long userId, List<TournamentStatus> statuses);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("update Participants p set p.task =:task " +
            "where p.tournament.id =:tournamentId and p.user.id =:userId")
    void updateTask(long tournamentId, long userId, String task);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("update Participants p set p.grade =:grade " +
            "where p.tournament.id =:tournamentId and p.user.id =:userId")
    void updateGrade(long tournamentId, long userId, long grade);

    @Transactional
    void deleteByTournamentId(long tournamentId);

    @Transactional
    void deleteByTournamentIdAndUserId(long tournamentId, long userId);
}
