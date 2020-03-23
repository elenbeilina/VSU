package com.aqualen.vsu.controllers;

import com.aqualen.vsu.services.TournamentsService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tournaments")
public class TournamentsController {

    @Autowired
    TournamentsService tournamentsService;

    @GetMapping("")
    public Response getAll() {
        return Response.success(tournamentsService.getAll());
    }
}
