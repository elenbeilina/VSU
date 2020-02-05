package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.repository.TournamentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TournamentsService {

    @Autowired
    TournamentRepository tournamentRepository;

    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }
}
