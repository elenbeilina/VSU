package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findBySponsorId(Long sponsorId);
}
