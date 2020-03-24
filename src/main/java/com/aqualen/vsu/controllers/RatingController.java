package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.RatingLogic;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    RatingLogic ratingLogic;

    @GetMapping("all")
    public Response getAll() {
        return Response.success(ratingLogic.getUsersList());
    }

    @GetMapping("")
    public Response getExactAmount(@RequestParam int count) {
        return Response.success(ratingLogic.getUsersList(count));
    }
}
