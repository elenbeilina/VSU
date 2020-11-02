package com.aqualen.vsu.controllers.tournament;

import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.services.TournamentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentsService tournamentsService;

    @SimpleLog
    @GetMapping
    public ResponseEntity<Tournament> get(@RequestParam Long id) {
        return ResponseEntity.ok(tournamentsService.getById(id));
    }

    @SimpleLog
    @PutMapping
    public void edit(@RequestBody Tournament tournament) {
        tournamentsService.update(tournament);
    }

    @SimpleLog
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        tournamentsService.delete(id);
    }

    @SimpleLog
    @PostMapping
    public ResponseEntity<Tournament> add(@RequestBody Tournament tournament) {
        return ResponseEntity.ok(tournamentsService.update(tournament));
    }
}
