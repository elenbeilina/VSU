package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.services.TournamentsService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/tournaments")
public class TournamentsController {
    @Autowired
    TournamentsService tournamentsService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с турнирами произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    public Response getAll() {
        return Response.success(tournamentsService.getAll());
    }

    @SimpleLog
    @GetMapping("")
    public Response getOne(@RequestParam Long id) {
        return Response.success(tournamentsService.getById(id));
    }

    @SimpleLog
    @PutMapping("")
    public Response edit(@RequestBody Tournament tournament) {
        tournamentsService.update(tournament);
        return Response.success();
    }

    @SimpleLog
    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        tournamentsService.delete(id);
        return Response.success();
    }

    @SimpleLog
    @PostMapping("")
    public Response add(@RequestBody Tournament tournament) {
        tournamentsService.update(tournament);
        return Response.success();
    }
}
