package com.aqualen.vsu.services;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.dto.TournamentForParticipant;
import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.logic.RatingLogic;
import com.aqualen.vsu.repository.ParticipantsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.dto.ParticipantResponse.toEntity;
import static com.aqualen.vsu.enums.TournamentStatus.CREATED;
import static com.aqualen.vsu.utils.UserUtils.getUserId;

@Service
@AllArgsConstructor
public class ParticipantsService {

    private final ModelMapper mapper;
    private final ParticipantsRepository repository;
    private final RatingLogic logic;

    public void addParticipant(Long tournamentId) {
        Long userId = getUserId();

        repository.saveAndFlush(Participants.builder()
                .id(new ParticipantKey(tournamentId, userId))
                .tournament(Tournament.builder().id(tournamentId).build())
                .user(User.builder().id(userId).build())
                .build());
    }

    public void deleteParticipant(Long tournamentId) {
        Long userId = getUserId();

        repository.deleteByTournamentIdAndUserId(tournamentId, userId);
    }

    public List<ParticipantResponse> getAllParticipants(int tournamentId) {
        return repository.findByTournamentId(tournamentId).stream()
                .map(participants -> mapper.map(participants, ParticipantResponse.class))
                .collect(Collectors.toList());
    }

    public List<TournamentForParticipant> getTournaments() {
        return repository.findByUserIdAndTournamentStatusNot(getUserId(), CREATED)
                .stream().map(TournamentForParticipant::toDto)
                .collect(Collectors.toList());
    }

    public void updateTask(long tournamentId, String task) {
        repository.updateTask(tournamentId, getUserId(), task);
    }

    public void gradeParticipants(long tournamentId, List<ParticipantResponse> participantList) {
        repository.saveAll(participantList.stream()
                .map(participant -> toEntity(participant, tournamentId))
                .collect(Collectors.toList()));

        logic.rateUsers(participantList);
    }
}
