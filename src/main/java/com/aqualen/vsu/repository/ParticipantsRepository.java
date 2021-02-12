package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, ParticipantKey> {
}
