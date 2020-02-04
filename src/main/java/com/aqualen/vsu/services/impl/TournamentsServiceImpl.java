package com.aqualen.vsu.services.impl;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.repository.TournamentRepository;
import com.aqualen.vsu.services.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentsServiceImpl implements TournamentsService {

    @Autowired
    TournamentRepository tournamentRepository;

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }
}
