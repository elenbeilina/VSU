package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.logic.RatingLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rating")
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
}
