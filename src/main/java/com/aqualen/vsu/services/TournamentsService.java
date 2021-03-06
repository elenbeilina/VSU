package com.aqualen.vsu.services;

import com.aqualen.vsu.dto.AddTournament;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.repository.ParticipantsRepository;
import com.aqualen.vsu.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.aqualen.vsu.dto.AddTournament.toEntity;

@Service
@RequiredArgsConstructor
public class TournamentsService {

    private final TournamentRepository tournamentRepository;
    private final ParticipantsRepository participantsRepository;

    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    public Tournament getById(long id) {
        return tournamentRepository.getOne(id);
    }

    public Tournament update(Tournament tournament) {
        return tournamentRepository.saveAndFlush(tournament);
    }

    public Tournament add(AddTournament dto) {
        Tournament tournament = toEntity(dto);

        return tournamentRepository.saveAndFlush(tournament);
    }

    @Transactional
    public void delete(Long id) {
        participantsRepository.deleteByTournamentId(id);
        tournamentRepository.deleteById(id);
    }
}
