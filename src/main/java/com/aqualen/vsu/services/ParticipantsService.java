package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.repository.ParticipantsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.aqualen.vsu.utils.UserUtils.getUserId;

@Service
@AllArgsConstructor
public class ParticipantsService {

    private final ParticipantsRepository repository;

    public void addParticipant(long tournamentId){
        Long userId = getUserId();

        repository.save(Participants.builder()
                .id(new ParticipantKey(tournamentId, userId))
                .build());
    }
}
