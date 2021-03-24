package com.aqualen.vsu.controllers;

import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.log.SimpleLog;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.logic.RatingLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingLogic ratingLogic;

    @SimpleLog
    @GetMapping
    public ResponseEntity<List<User>> getAll(@PageableDefault(size = 50) Pageable pageable, @RequestParam TechnologyName technologyName) {
        return ResponseEntity.ok(ratingLogic.getUsersList(technologyName, pageable));
    }
}
