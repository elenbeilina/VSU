package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.logic.RatingLogic;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    RatingLogic ratingLogic;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с рейтингом произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    public Response getAll() {
        return Response.success(ratingLogic.getUsersList());
    }

    @SimpleLog
    @GetMapping("")
    public Response getExactAmount(@RequestParam int count) {
        return Response.success(ratingLogic.getUsersList(count));
    }
}
