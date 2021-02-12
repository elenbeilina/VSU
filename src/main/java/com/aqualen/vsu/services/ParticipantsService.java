package com.aqualen.vsu.services;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.dto.TournamentForParticipant;
import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.enums.TournamentStatus;
import com.aqualen.vsu.repository.ParticipantsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.utils.UserUtils.getUserId;

@Service
@AllArgsConstructor
public class ParticipantsService {

    //private final ModelMapper mapper;
    private final ParticipantsRepository repository;

    public void addParticipant(int tournamentId) {
        Long userId = getUserId();

        repository.save(Participants.builder()
                .id(new ParticipantKey(tournamentId, userId))
                .build());
    }

    //TODO:use mapper
    public List<ParticipantResponse> getAllParticipants(int tournamentId) {
        //return repository.findByTournamentId(tournamentId).stream().map().collect(Collectors.toList());
        return null;
    }

    public List<TournamentForParticipant> getTournaments() {
        return repository.findByUserIdAndTournamentStatusNot(getUserId(), TournamentStatus.CREATED)
                .stream().map(TournamentForParticipant::toDto)
                .collect(Collectors.toList());
    }
}
