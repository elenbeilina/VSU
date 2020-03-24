package com.aqualen.vsu.controllers;

import com.aqualen.vsu.aspects.SimpleLog;
import com.aqualen.vsu.entity.Question;
import com.aqualen.vsu.services.QuestionService;
import com.aqualen.vsu.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    private static final String DEFAULT_ERROR_MESSAGE = "При работе с вопросами/ответами произошла непредвиденная ошибка";

    @ExceptionHandler(Exception.class)
    public Response handleException(HttpServletRequest request, Exception e) {
        log.error("Error on processing of URL mapping '" + request.getRequestURI() + "': ", e);
        return Response.fail(DEFAULT_ERROR_MESSAGE);
    }

    @SimpleLog
    @GetMapping("all")
    public Response getAll() {
        return Response.success(questionService.getAll());
    }

    @SimpleLog
    @GetMapping("")
    public Response getOne(@RequestParam Long id) {
        return Response.success(questionService.getById(id));
    }

    @SimpleLog
    @PutMapping("")
    public Response edit(@RequestBody Question question) {
        questionService.update(question);
        return Response.success();
    }

    @SimpleLog
    @DeleteMapping("")
    public Response delete(@RequestParam Long id) {
        questionService.delete(id);
        return Response.success();
    }

    @SimpleLog
    @PostMapping("")
    public Response add(@RequestBody Question question) {
        questionService.update(question);
        return Response.success();
    }
}
