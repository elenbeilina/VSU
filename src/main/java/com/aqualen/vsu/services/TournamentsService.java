package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentsService {

    @Autowired
    TournamentRepository tournamentRepository;

    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    public Tournament getById(long id) {
        return tournamentRepository.getOne(id);
    }

    public void update(Tournament tournament) {
        tournamentRepository.saveAndFlush(tournament);
    }

    public void delete(Long id) {
        tournamentRepository.deleteById(id);
    }
}
