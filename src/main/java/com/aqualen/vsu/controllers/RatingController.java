package com.aqualen.vsu.controllers;

import com.aqualen.vsu.dto.RateRequest;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.logic.RatingLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingLogic ratingLogic;

    @SimpleLog
    @GetMapping("all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(ratingLogic.getUsersList());
    }

    @SimpleLog
    @GetMapping
    public ResponseEntity<List<User>> getExactAmount(@RequestParam int count) {
        return ResponseEntity.ok(ratingLogic.getUsersList(count));
    }

    @SimpleLog
    @PutMapping("rate")
    public void rateUsers(@RequestBody List<RateRequest> requests) {
        ratingLogic.rateUsers(requests);
    }
}
