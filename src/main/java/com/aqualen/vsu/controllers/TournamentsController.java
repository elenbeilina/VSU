package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.services.TournamentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentsController {

    private final TournamentsService tournamentsService;


    @SimpleLog
    @GetMapping("all")
    public ResponseEntity<List<Tournament>> getAll() {
        return ResponseEntity.ok(tournamentsService.getAll());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<Tournament> getOne(@RequestParam Long id) {
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
