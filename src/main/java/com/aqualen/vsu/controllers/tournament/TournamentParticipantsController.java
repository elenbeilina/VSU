package com.aqualen.vsu.controllers.tournament;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.services.ParticipantsService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tournament/participant")
@AllArgsConstructor
public class TournamentParticipantsController {
    private final ParticipantsService participantsService;

    @SimpleLog
    @PostMapping
    @ApiOperation("Method for adding user to tournament")
    public void addParticipant(@RequestParam int tournamentId){
        participantsService.addParticipant(tournamentId);
    }

    @SimpleLog
    @GetMapping("all")
    @ApiOperation("Method for getting all participants with their tasks to rate")
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants(@RequestParam int tournamentId){
        return ResponseEntity.ok(participantsService.getAllParticipants(tournamentId));
    }
}
