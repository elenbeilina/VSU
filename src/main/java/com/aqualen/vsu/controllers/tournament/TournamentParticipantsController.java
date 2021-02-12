package com.aqualen.vsu.controllers.tournament;

import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.services.ParticipantsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tournament/participant")
@AllArgsConstructor
public class TournamentParticipantsController {
    private final ParticipantsService participantsService;

    @SimpleLog
    @PostMapping
    public void addParticipant(@RequestParam long tournamentId){
        participantsService.addParticipant(tournamentId);
    }
}
